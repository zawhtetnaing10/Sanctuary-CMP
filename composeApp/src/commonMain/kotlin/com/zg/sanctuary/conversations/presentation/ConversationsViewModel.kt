package com.zg.sanctuary.conversations.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zg.sanctuary.conversations.data.repository.ConversationsRepository
import com.zg.sanctuary.friends.data.repositories.FriendsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConversationsViewModel(
    val conversationsRepository: ConversationsRepository,
    val friendsRepository: FriendsRepository,
) : ViewModel() {
    // State
    private val _state = MutableStateFlow(ConversationsState())
    val state = _state
        .onStart {
            _state.update { it.copy(isLoading = true) }

            // Get Friends
            friendsRepository.getFriends(
                onSuccess = { friends ->
                    _state.update { it.copy(friends = friends , isLoading = false) }
                },
                onFailure = { errMsg ->
                    _state.update { it.copy(isLoading = false, error = errMsg) }
                }
            )

            // Get Conversations
            conversationsRepository.getConversations (
                onSuccess = { conversations ->
                    _state.update { it.copy(conversations = conversations, isLoading = false) }
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

    // Events
    private val _events = Channel<ConversationsEvents>()
    val events = _events.receiveAsFlow()

    // Handle Action
    fun handleAction(action : ConversationsAction){
        when(action){
            ConversationsAction.OnTapBack -> {
                viewModelScope.launch {
                    _events.send(ConversationsEvents.NavigateBack)
                }
            }
            is ConversationsAction.OnTapConversation -> {
                viewModelScope.launch {
                    _events.send(ConversationsEvents.NavigateToChat(action.userId))
                }
            }
            is ConversationsAction.OnTapFriend -> {
                viewModelScope.launch {
                    _events.send(ConversationsEvents.NavigateToChat(action.userId))
                }
            }
        }
    }
}