package com.zg.sanctuary.chat.presentation

import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.chat.domain.ChatMessage

// Chat State
data class ChatState(
    // TODO: - Change back to ChatMessage after testing
    //val chatMessages: List<ChatMessage> = listOf(),
    val chatMessages: List<String> = listOf(),
    val currentMessage : String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val loggedInUser: User? = null,
    val recipient: User? = null
)