package com.zg.sanctuary.posts.presentation.post_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PostListViewModel : ViewModel() {
    // State
    private val _state = MutableStateFlow<PostListState>(PostListState())
    val state: StateFlow<PostListState> = _state.asStateFlow()

    // Events
    private val _events = Channel<PostListEvent>()
    val events = _events.receiveAsFlow()

    fun handleAction(action: PostListAction) {
        when (action) {
            is PostListAction.OnTapComment -> {
                viewModelScope.launch {
                    _events.send(PostListEvent.NavigateToPostDetails(action.id))
                }
            }

            is PostListAction.OnTapCreatePost -> {
                viewModelScope.launch {
                    _events.send(PostListEvent.NavigateToCreatePost())
                }
            }

            is PostListAction.OnTapLike -> {
                // TODO: - Handle Like
            }

            is PostListAction.OnTapPost -> {
                viewModelScope.launch {
                    _events.send(PostListEvent.NavigateToPostDetails(action.id))
                }
            }

            is PostListAction.OnTapSearch -> {
                // TODO: - Handle Search
            }

            is PostListAction.OnTapShare -> {
                // TODO: - Handle Share
            }
        }
    }
}