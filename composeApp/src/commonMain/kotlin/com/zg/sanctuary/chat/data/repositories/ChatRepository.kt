package com.zg.sanctuary.chat.data.repositories

import com.zg.sanctuary.chat.data.network.ChatApiService
import com.zg.sanctuary.chat.data.network.WebsocketService
import com.zg.sanctuary.chat.domain.ChatHistoryResponse
import com.zg.sanctuary.core.data.network.onError
import com.zg.sanctuary.core.data.network.onSuccess
import com.zg.sanctuary.core.persistence.SanctuaryDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ChatRepository(
    private val websocketService: WebsocketService,
    private val applicationScope: CoroutineScope,
    val chatApiService: ChatApiService,
    val database: SanctuaryDatabase
) {

    // Shared flow for chat messages
    private val _chatMessages = MutableSharedFlow<String>()

    init {
        applicationScope.launch {
            websocketService.incomingMessages
                .onEach { chatMessage ->
                    _chatMessages.emit(chatMessage)
                }
                .collect()
        }
    }

    // Chat message history.
    suspend fun getChatMessageHistory(
        otherUserId: Int,
        onSuccess: (ChatHistoryResponse) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val loggedInUser = database.userDao().getLoggedInUser()
        loggedInUser?.let {
            val accessToken = it.getBearerToken()
            chatApiService.getChatHistory(
                otherUserId = otherUserId,
                accessToken = accessToken
            ).onSuccess {
                onSuccess(it)
            }.onError {
                onFailure(it.error)
            }
        }
    }

    // Connect web socket
    suspend fun connectWebSocket(conversationId : Int) {
        val loggedInUser = database.userDao().getLoggedInUser()

        loggedInUser?.let {
            val accessToken = it.getBearerToken()
            websocketService.connect(accessToken = accessToken, conversationId = conversationId)
        }
    }

    // Disconnect web socket
    suspend fun disconnectWebSocket() {
        websocketService.disconnect()
    }

    // Get all messages reactively from web socket
    fun getMessagesForChat(): Flow<List<String>> {
        return _chatMessages.map { message ->
            listOf(message)
        }
    }

    // Send message via web socket
    suspend fun sendMessage(receiverId: Int, message: String) {
        val loggedInUser = database.userDao().getLoggedInUser()
        loggedInUser?.let {
            websocketService.sendMessage(
                accessToken = it.getBearerToken(),
                message = message,
                receiverId = receiverId
            )
        }
    }
}