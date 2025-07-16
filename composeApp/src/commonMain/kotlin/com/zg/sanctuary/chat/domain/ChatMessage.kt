package com.zg.sanctuary.chat.domain

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    @SerialName("id")
    val id: Int,
    @SerialName("content")
    val content: String,
    @SerialName("created_at")
    val createdAt: Instant,
    @SerialName("updated_at")
    val updatedAt: Instant,
    @SerialName("conversation_id")
    val conversationId: Int? = null,
    @SerialName("sender_id")
    val senderId: Int
)