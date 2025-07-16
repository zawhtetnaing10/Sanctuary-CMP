package com.zg.sanctuary.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zg.sanctuary.auth.data.repositories.AuthRepository
import com.zg.sanctuary.chat.data.repositories.ChatRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    val receiverId: Int,
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    // State
    private val _state = MutableStateFlow(ChatState())
    val state = _state
        .onStart {
            // TODO: - Load chat history here. Only when it's completed connect to web socket
            viewModelScope.launch {
                chatRepository.connectWebSocket()
            }

            // Observe the chat messages
            observeChatMessages()

            // Load logged in user
            val loggedInUser = authRepository.getLoggedInUser()
            _state.update { it.copy(loggedInUser = loggedInUser) }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    // Events
    private val _events = Channel<ChatEvents>()
    val events = _events.receiveAsFlow()

    fun observeChatMessages() {
        viewModelScope.launch {
            chatRepository.getMessagesForChat()
                .collect { messages ->
                    _state.update {
                        // Add the messages from repo to state
                        val tmpMessages = it.chatMessages.toMutableList()
                        tmpMessages.addAll(messages)

                        // Return the updated state.
                        it.copy(chatMessages = tmpMessages)
                    }
                }
        }
    }

    fun handleAction(action: ChatActions) {
        when (action) {
            is ChatActions.OnChangeMessage -> {
                _state.update { it.copy(currentMessage = action.message) }
            }

            ChatActions.OnErrorDialogDismissed -> {
                _state.update { it.copy(error = "") }
            }

            ChatActions.OnSendMessage -> {
                viewModelScope.launch {
                    chatRepository.sendMessage(
                        receiverId = receiverId,
                        message = _state.value.currentMessage
                    )
                    _state.update { it.copy(currentMessage = "") }

                    _events.send(ChatEvents.RemoveFocus)
                }
            }

            ChatActions.OnTapBack -> {
                viewModelScope.launch {
                    _events.send(ChatEvents.NavigateBack)
                }
            }
        }
    }
}