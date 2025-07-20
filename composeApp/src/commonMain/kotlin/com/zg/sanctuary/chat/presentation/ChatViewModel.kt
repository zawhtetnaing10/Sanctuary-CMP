package com.zg.sanctuary.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zg.sanctuary.auth.data.repositories.AuthRepository
import com.zg.sanctuary.chat.data.repositories.ChatRepository
import com.zg.sanctuary.chat.domain.ChatMessage
import com.zg.sanctuary.chat.domain.OutgoingMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ChatViewModel(
    val otherUserId: Int,
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    // State
    private val _state = MutableStateFlow(ChatState())
    val state = _state
        .onStart {

            _state.update { it.copy(isLoading = true) }

            // Load Chat History
            viewModelScope.launch {
                chatRepository.getChatMessageHistory(
                    otherUserId = otherUserId,
                    onSuccess = { chatHistoryResponse ->

                        _state.update {
                            it.copy(
                                chatMessages = chatHistoryResponse.chatMessages,
                                otherUser = chatHistoryResponse.user,
                                conversationId = chatHistoryResponse.conversationId
                            )
                        }

                        // Scroll to bottom.
                        viewModelScope.launch {
                            _events.send(ChatEvents.ScrollToBottom(_state.value.chatMessages.count() - 1))
                        }

                        // Connect to web socket after fetching the chat message history
                        viewModelScope.launch {
                            chatRepository.connectWebSocket(chatHistoryResponse.conversationId)
                        }
                    },
                    onFailure = { errMsg ->
                        _state.update { it.copy(isLoading = false, error = errMsg) }
                    }
                )
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

    // Clean up for websocket
    private val cleanupScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    override fun onCleared() {

        cleanupScope.launch {
            chatRepository.disconnectWebSocket()
        }

        super.onCleared()
    }

    // Events
    private val _events = Channel<ChatEvents>()
    val events = _events.receiveAsFlow()

    // Observe chat messages from websocket
    fun observeChatMessages() {
        viewModelScope.launch {
            chatRepository.getMessagesForChat()
                .collect { messages ->
                    println("Receiving message =====> $messages")
                    try {
                        _state.update {
                            // Add the messages from repo to state
                            val tmpMessages = it.chatMessages.toMutableList()

                            // Decode the message.
                            val decodedMessages : List<ChatMessage> = messages.map {
                                return@map Json.decodeFromString<ChatMessage>(it)
                            }.toList()
                            tmpMessages.addAll(decodedMessages)

                            // Return the updated state.
                            it.copy(chatMessages = tmpMessages)
                        }
                        _events.send(ChatEvents.ScrollToBottom(_state.value.chatMessages.count() - 1))
                    } catch(_: Exception){
                        //  Message returned is not json. It's fine.
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
                    // Create outgoing message and encode it.
                    val outgoingMessage = OutgoingMessage(
                        content = _state.value.currentMessage,
                        senderId = _state.value.loggedInUser!!.id,
                        conversationId = _state.value.conversationId
                    )
                    val outgoingMessageString = Json.encodeToString(outgoingMessage)

                    // Send the message
                    chatRepository.sendMessage(
                        receiverId = otherUserId,
                        message = outgoingMessageString
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