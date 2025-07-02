package com.zg.sanctuary.posts.presentation.post_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zg.sanctuary.posts.data.repositories.PostRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailsViewModel(
    val postRepository: PostRepository,
    val postId: Int
) : ViewModel() {

    // State
    private val _state = MutableStateFlow(PostDetailsState())
    val state = _state
        .onStart {
            // Get post details
            postRepository.getPostById(
                postId = postId,
                onSuccess = { post ->
                    _state.update { it.copy(post = post) }
                },
                onFailure = { errMsg ->
                    _state.update { it.copy(error = errMsg) }
                }
            )

            // Get post comments
            getCommentsForPost()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    // Events
    private val _events = Channel<PostDetailsEvent>()
    val events = _events.receiveAsFlow()

    fun handleAction(action: PostDetailsAction) {
        when (action) {
            is PostDetailsAction.OnCommentChanged -> {
                _state.update {
                    it.copy(currentComment = action.comment)
                }
            }

            is PostDetailsAction.OnTapBack -> {
                viewModelScope.launch {
                    _events.send(PostDetailsEvent.NavigateBack)
                }
            }

            is PostDetailsAction.OnTapLike -> {
                _state.value.post?.let { post ->

                    // Update like state
                    val likeCount = if (post.likedByUser) post.likeCount - 1 else post.likeCount + 1
                    val tmpPost = post.copy(likedByUser = !post.likedByUser, likeCount = likeCount)

                    _state.update { it.copy(post = tmpPost) }

                    // Make api call
                    viewModelScope.launch {
                        postRepository.likePost(postId = post.id, onSuccess = {}, onFailure = { errMsg ->
                            _state.update { it.copy(error = errMsg) }
                        })
                    }
                }
            }

            is PostDetailsAction.OnTapPostComment -> {
                _state.value.post?.let { post ->
                    viewModelScope.launch {
                        postRepository.createComment(postId = post.id, content = _state.value.currentComment, onSuccess = {
                            viewModelScope.launch {
                                getCommentsForPost()
                            }

                            // Reset comment
                            _state.update {
                                it.copy(currentComment = "")
                            }

                            // Send clear focus event
                            viewModelScope.launch {
                                _events.send(PostDetailsEvent.RemoveFocus)
                            }

                        }, onFailure = { errMsg ->
                            _state.update { it.copy(error = errMsg) }
                        })
                    }
                }
            }
        }
    }

    // Function to get comments for post
    private suspend fun getCommentsForPost() {
        postRepository.getCommentsForPost(
            postId = postId,
            onSuccess = { comments ->
                _state.update { it.copy(comments = comments) }
            },
            onFailure = { errMsg ->
                _state.update { it.copy(error = errMsg) }
            }
        )
    }
}