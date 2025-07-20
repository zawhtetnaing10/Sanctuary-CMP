package com.zg.sanctuary.chat.presentation

import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.chat.domain.ChatMessage

// Chat State
data class ChatState(
    val chatMessages: List<ChatMessage> = listOf(),
    val currentMessage : String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val loggedInUser: User? = null,
    val otherUser: User? = null,
    val conversationId : Int = 0
)