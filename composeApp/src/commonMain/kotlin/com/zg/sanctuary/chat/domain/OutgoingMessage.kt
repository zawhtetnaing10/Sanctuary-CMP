package com.zg.sanctuary.chat.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OutgoingMessage(
    @SerialName("content")
    val content: String,
    @SerialName("sender_id")
    val senderId : Int,
    @SerialName("conversation_id")
    val conversationId : Int,
)