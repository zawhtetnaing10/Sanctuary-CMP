package com.zg.sanctuary.conversations.domain

import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.chat.domain.ChatMessage
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConversationResponse(
    @SerialName("id")
    val id : Int,
    @SerialName("conversation_name")
    val conversationName : String,
    @SerialName("conversation_type")
    val conversationType : String,
    @SerialName("created_at")
    val createdAt : Instant,
    @SerialName("updated_at")
    val updatedAt : Instant,
    @SerialName("last_message")
    val lastMessage : ChatMessage?,
    @SerialName("user")
    val user: User
)