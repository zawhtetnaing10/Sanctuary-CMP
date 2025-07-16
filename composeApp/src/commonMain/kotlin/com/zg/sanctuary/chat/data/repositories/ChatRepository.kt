package com.zg.sanctuary.chat.data.repositories

import com.zg.sanctuary.chat.data.network.WebsocketService
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
    val database : SanctuaryDatabase
) {

    // Shared flow for chat messages
    private val _chatMessages = MutableSharedFlow<String>()

    init {
        applicationScope.launch {
            websocketService.incomingMessages
                .onEach { chatMessage ->
                    // TODO: - Decode this to ChatMessage data later
                    _chatMessages.emit(chatMessage)
                }
                .collect()
        }
    }

    // Connect web socket
    suspend fun connectWebSocket(){
        websocketService.connect()
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
    suspend fun sendMessage(receiverId : Int, message : String){
        val loggedInUser = database.userDao().getLoggedInUser()
        loggedInUser?.let {
            // TODO: - Encode the message to ChatMessage json string.
            websocketService.sendMessage(
                accessToken = it.getBearerToken(),
                message = message,
                receiverId = receiverId
            )
        }
    }
}