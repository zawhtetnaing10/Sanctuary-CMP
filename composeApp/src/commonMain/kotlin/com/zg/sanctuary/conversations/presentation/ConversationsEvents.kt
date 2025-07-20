package com.zg.sanctuary.conversations.presentation

sealed interface ConversationsEvents {
    data object NavigateBack : ConversationsEvents
    data class NavigateToChat(val userId : Int) : ConversationsEvents
}