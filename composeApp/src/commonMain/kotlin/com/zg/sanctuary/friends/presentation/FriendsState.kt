package com.zg.sanctuary.friends.presentation

import com.zg.sanctuary.friends.domain.FriendRequest

data class FriendsState(
    val error : String = "",
    val isLoading : Boolean = false,
    val friendRequests : List<FriendRequest> = listOf(),
    val friends : List<FriendRequest> = listOf()
)