package com.zg.sanctuary.posts.presentation.post_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zg.sanctuary.posts.data.repositories.PostRepository
import com.zg.sanctuary.posts.domain.Post
import com.zg.sanctuary.posts.presentation.post_list.PostListEvent.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostListViewModel(
    private val postRepository: PostRepository
) : ViewModel() {
    // State
    private val _state = MutableStateFlow<PostListState>(PostListState())
    val state: StateFlow<PostListState> = _state.asStateFlow()

    // Events
    private val _events = Channel<PostListEvent>()
    val events = _events.receiveAsFlow()

    fun handleAction(action: PostListAction) {
        when (action) {
            is PostListAction.OnPostListScreenReached -> {
                viewModelScope.launch {
                    // Call post list api
                    postRepository.getPosts(
                        onSuccess = { metaResponse ->
                            _state.update {
                                it.copy(posts = metaResponse.data, meta = metaResponse.meta)
                            }
                        },
                        onFailure = { errMsg ->
                            _state.update {
                                it.copy(error= errMsg)
                            }
                        }
                    )
                }
            }

            is PostListAction.OnListEndReached -> {
                viewModelScope.launch {
                    if (!_state.value.isLoadingMore) {
                        _state.update { it.copy(isLoadingMore = true) }
                        postRepository.getMorePosts(
                            _state.value.meta?.nextPage ?: 0,
                            onSuccess = { metaResponse ->
                                _state.update {
                                    it.copy(isLoadingMore = false)
                                }
                                // Update data
                                metaResponse?.let {
                                    val tempPosts = _state.value.posts.toMutableList()
                                    tempPosts.addAll(metaResponse.data)
                                    _state.update {
                                        it.copy(posts = tempPosts, meta = metaResponse.meta)
                                    }
                                }
                            },
                            onFailure = { errMsg ->
                                _state.update {
                                    it.copy(error = errMsg, isLoadingMore = false)
                                }
                            })
                    }
                }
            }

            is PostListAction.OnTapComment -> {
                viewModelScope.launch {
                    _events.send(NavigateToPostDetails(action.id))
                }
            }

            is PostListAction.OnTapCreatePost -> {
                viewModelScope.launch {
                    _events.send(NavigateToCreatePost())
                }
            }

            is PostListAction.OnTapLike -> {
                // Update like state
                val tempPosts : List<Post> = _state.value.posts.map {
                    if(it.id == action.id){
                        val likeCount = if(it.likedByUser) it.likeCount - 1 else it.likeCount + 1
                        it.copy(likedByUser = !it.likedByUser, likeCount = likeCount)
                    } else {
                        it
                    }
                }.toList()
                _state.update {
                    it.copy(posts = tempPosts)
                }

                // Make api call
                viewModelScope.launch {
                    postRepository.likePost(action.id, onSuccess = {}, onFailure = { errMsg ->
                        _state.update {
                            it.copy(error = errMsg)
                        }
                    })
                }
            }

            is PostListAction.OnTapPost -> {
                viewModelScope.launch {
                    _events.send(NavigateToPostDetails(action.id))
                }
            }

            is PostListAction.OnTapSearch -> {
                // TODO: - Handle Search after search feature has been added
            }

            is PostListAction.OnTapShare -> {
                // TODO: - Handle Share after share feature has been added
            }

            is PostListAction.OnErrorDialogDismissed -> {
                _state.update {
                    it.copy(error = "")
                }
            }
        }
    }
}