package com.zg.sanctuary.friends.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zg.sanctuary.friends.data.repositories.FriendsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FriendsViewModel(
    val friendsRepository: FriendsRepository,
) : ViewModel() {
    // State
    private val _state = MutableStateFlow(FriendsState())
    val state = _state
        .onStart {
            refreshFriendRequestsAndFriends()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    // Get Friend requests and friends
    private suspend fun refreshFriendRequestsAndFriends() {
        // Friend Requests
        friendsRepository.getFriendRequests(
            onSuccess = { friendRequests ->
                _state.update { it.copy(friendRequests = friendRequests) }
            },
            onFailure = { errMsg ->
                _state.update { it.copy(isLoading = false, error = errMsg) }
            }
        )

        // Friends
        friendsRepository.getFriends(
            onSuccess = { friends ->
                _state.update { it.copy(friends = friends) }
            },
            onFailure = { errMsg ->
                _state.update { it.copy(isLoading = false, error = errMsg) }
            }
        )
    }

    // Event
    private val _events = Channel<FriendsEvent>()
    val events = _events.receiveAsFlow()

    fun handleAction(action: FriendsAction) {
        when (action) {
            is FriendsAction.OnAcceptFriendRequest -> {
                viewModelScope.launch {
                    friendsRepository.acceptFriendRequest(
                        frId = action.frId,
                        onSuccess = {
                            viewModelScope.launch {
                                refreshFriendRequestsAndFriends()
                            }
                        },
                        onFailure = { errMsg ->
                            _state.update { it.copy(isLoading = false, error = errMsg) }
                        }
                    )
                }
            }

            FriendsAction.OnErrorDialogDismissed -> {
                _state.update { it.copy(error = "") }
            }

            is FriendsAction.OnTapProfile -> {
                viewModelScope.launch {
                    _events.send(FriendsEvent.NavigateToUserProfile(action.userId))
                }
            }
        }
    }
}