package com.zg.sanctuary.conversations.presentation

import com.zg.sanctuary.conversations.domain.ConversationResponse
import com.zg.sanctuary.friends.domain.FriendRequest

data class ConversationsState(
    val isLoading : Boolean = false,
    val error : String = "",
    val friends: List<FriendRequest> = listOf(),
    val conversations: List<ConversationResponse> = listOf()
)