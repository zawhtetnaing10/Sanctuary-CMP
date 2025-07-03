package com.zg.sanctuary.posts.presentation.create_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zg.sanctuary.auth.data.repositories.AuthRepository
import com.zg.sanctuary.posts.data.repositories.PostRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreatePostViewModel(
    private val postRepo: PostRepository,
    private val authRepo: AuthRepository
) : ViewModel() {
    // State
    private val _state = MutableStateFlow(CreatePostState())
    val state = _state.onStart {
        // Get the logged in user initially
        val loggedInUser = authRepo.getLoggedInUser()
        _state.update {
            it.copy(loggedInUser = loggedInUser)
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    // Event
    private val _events = Channel<CreatePostEvents>()
    val events = _events.receiveAsFlow()

    fun handleAction(action: CreatePostAction) {
        when (action) {
            is CreatePostAction.OnChangeContent -> {
                _state.update { it.copy(content = action.content) }
            }

            is CreatePostAction.OnChangeImage -> {
                _state.update { it.copy(image = action.image) }
            }

            CreatePostAction.OnTapBack -> {
                viewModelScope.launch {
                    _events.send(CreatePostEvents.NavigateBack)
                }
            }

            CreatePostAction.OnTapCreatePost -> {
                // Validation
                if (_state.value.content.isEmpty()) {
                    _state.update { it.copy(error = "Content cannot be empty", isLoading = false) }
                    return
                }

                // Loading
                _state.update { it.copy(isLoading = true) }

                // Api Call
                viewModelScope.launch {
                    postRepo.createPost(
                        content = state.value.content,
                        file = if (state.value.image.isEmpty()) null else state.value.image,
                        onSuccess = {
                            _state.update { it.copy(isLoading = false) }
                            viewModelScope.launch {
                                _events.send(CreatePostEvents.NavigateBack)
                            }
                        },
                        onFailure = { errMsg ->
                            _state.update { it.copy(isLoading = false, error = errMsg) }
                        }
                    )
                }
            }

            CreatePostAction.OnNavigateBack -> {
                viewModelScope.launch {
                    _events.send(CreatePostEvents.NavigateBack)
                }
            }

            CreatePostAction.OnDeleteImage -> {
                _state.update { it.copy(image = byteArrayOf()) }
            }

            CreatePostAction.OnErrorDialogDismissed -> {
                _state.update { it.copy(error = "") }
            }
        }
    }
}