package com.zg.sanctuary.chat.domain

import com.zg.sanctuary.auth.domain.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatHistoryResponse(
    @SerialName("conversation_id")
    val conversationId : Int,
    @SerialName("user")
    val user : User,
    @SerialName("chat_messages")
    val chatMessages : List<ChatMessage>
)