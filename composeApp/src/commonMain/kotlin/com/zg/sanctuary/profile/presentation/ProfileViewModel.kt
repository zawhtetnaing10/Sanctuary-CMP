package com.zg.sanctuary.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zg.sanctuary.auth.data.repositories.AuthRepository
import com.zg.sanctuary.friends.data.repositories.FriendsRepository
import com.zg.sanctuary.friends.domain.FRIEND_REQUEST_ACCEPTED
import com.zg.sanctuary.friends.domain.FRIEND_REQUEST_PENDING
import com.zg.sanctuary.posts.data.repositories.PostRepository
import com.zg.sanctuary.posts.domain.Post
import com.zg.sanctuary.profile.data.repository.ProfileRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    val userId: Int,
    val profileRepository: ProfileRepository,
    val authRepository: AuthRepository,
    val postRepository: PostRepository,
    val friendsRepository: FriendsRepository,
) : ViewModel() {
    // State
    private val _state = MutableStateFlow(ProfileState())
    val state = _state
        .onStart {
            // Get logged in user
            val loggedInUser = authRepository.getLoggedInUser()
            _state.update { it.copy(loggedInUser = loggedInUser) }

            // Get User Profile
            getUserProfile()

            // Get posts by user
            postRepository.getPostsForUser(
                userId = userId,
                onSuccess = { posts ->
                    _state.update { it.copy(postsByUser = posts) }
                },
                onFailure = { errMsg ->
                    _state.update { it.copy(isLoading = false, error = errMsg) }
                }
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    // Get user profile
    private suspend fun getUserProfile() {
        // Show Loading
        _state.update { it.copy(isLoading = true) }
        // Get All interests initially
        profileRepository.getUserDetails(
            userId = userId,
            onSuccess = { userDetails ->

                // Update user details state
                _state.update { it.copy(isLoading = false, userProfileDetails = userDetails) }

                // Update user status
                viewModelScope.launch {
                    val loggedInUser = _state.value.loggedInUser
                    loggedInUser?.let {
                        // Profile is for logged in user
                        if (loggedInUser.id == userDetails.user.id) {
                            _state.update { it.copy(userStatus = UserStatus.LOGGED_IN_USER) }
                            return@launch
                        }

                        // Not logged in user
                        val friendStatusList = userDetails.friendRequests
                        if (friendStatusList.isNotEmpty()) {
                            val friendStatus = friendStatusList.first()

                            if (friendStatus.senderId == loggedInUser.id && friendStatus.requestStatus == FRIEND_REQUEST_PENDING) {
                                // Logged in user sent the friend request. It is pending.
                                _state.update { it.copy(userStatus = UserStatus.FRIEND_REQUEST_PENDING) }
                                return@launch
                            } else if (friendStatus.receiverId == loggedInUser.id && friendStatus.requestStatus == FRIEND_REQUEST_PENDING) {
                                // Logged in user received the friend request
                                _state.update { it.copy(userStatus = UserStatus.RECEIVED_FRIEND_REQUEST) }
                                return@launch
                            } else if (friendStatus.requestStatus == FRIEND_REQUEST_ACCEPTED) {
                                // Already friends
                                _state.update { it.copy(userStatus = UserStatus.FRIENDS) }
                                return@launch
                            }
                        } else {
                            // No interactions with this user yet. Show send friend request button
                            _state.update { it.copy(userStatus = UserStatus.ABLE_TO_SEND_FRIEND_REQUEST) }
                            return@launch
                        }
                    }
                }
            },
            onFailure = { errMsg ->
                _state.update { it.copy(isLoading = false, error = errMsg) }
            }
        )
    }

    // Events
    private val _events = Channel<ProfileEvents>()
    val events = _events.receiveAsFlow()

    fun handleAction(action: ProfileActions) {
        when (action) {
            ProfileActions.OnTapSendFriendRequest -> {
                _state.value.loggedInUser?.let { loggedInUser ->
                    viewModelScope.launch {
                        _state.update { it.copy(isLoading = true) }
                        friendsRepository.createFriendRequest(
                            senderId = loggedInUser.id,
                            receiverId = userId,
                            onSuccess = {
                                viewModelScope.launch { getUserProfile() }
                            },
                            onFailure = { errMsg ->
                                _state.update { it.copy(isLoading = false, error = errMsg) }
                            }
                        )
                    }
                }
            }

            ProfileActions.OnAcceptFriendRequest -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }

                    _state.value.userProfileDetails?.friendRequests?.firstOrNull()?.id?.let { frId ->
                        friendsRepository.acceptFriendRequest(
                            frId = frId,
                            onSuccess = {
                                viewModelScope.launch { getUserProfile() }
                            },
                            onFailure = { errMsg ->
                                _state.update { it.copy(isLoading = false, error = errMsg) }
                            }
                        )
                    }
                }
            }

            is ProfileActions.OnErrorDialogDismissed -> {
                _state.update { it.copy(error = "") }
            }

            is ProfileActions.OnTapComment -> {
                viewModelScope.launch {
                    _events.send(ProfileEvents.NavigateToPostDetails(action.postId))
                }
            }

            is ProfileActions.OnTapLike -> {
                // Change post like state
                val tempPosts: List<Post> = _state.value.postsByUser.map {
                    if (it.id == action.postId) {
                        val likeCount = if (it.likedByUser) it.likeCount - 1 else it.likeCount + 1
                        it.copy(likedByUser = !it.likedByUser, likeCount = likeCount)
                    } else {
                        it
                    }
                }.toList()
                _state.update {
                    it.copy(postsByUser = tempPosts)
                }

                // Call api to like post
                viewModelScope.launch {
                    postRepository.likePost(postId = action.postId, onSuccess = {}, onFailure = { errMsg ->
                        _state.update { it.copy(error = errMsg) }
                    })
                }
            }

            ProfileActions.OnTapLogout -> {
                viewModelScope.launch {
                    authRepository.logout(
                        onSuccess = {
                            viewModelScope.launch {
                                _events.send(ProfileEvents.NavigateBackToLogin)
                            }
                        },
                        onFailure = { errMsg ->
                            _state.update { it.copy(error = errMsg) }
                        }
                    )
                }
            }

            is ProfileActions.OnTapPost -> {
                viewModelScope.launch {
                    _events.send(ProfileEvents.NavigateToPostDetails(action.postId))
                }
            }

            ProfileActions.OnTapBack -> {
                viewModelScope.launch {
                    _events.send(ProfileEvents.NavigateBack)
                }
            }
        }
    }
}