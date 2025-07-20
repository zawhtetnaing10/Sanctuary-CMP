package com.zg.sanctuary.conversations.presentation

import com.zg.sanctuary.AppRoute

sealed interface ConversationsAction {
    data object OnTapBack : ConversationsAction
    data class OnTapFriend(val userId : Int) : ConversationsAction
    data class OnTapConversation(val userId : Int) : ConversationsAction
}