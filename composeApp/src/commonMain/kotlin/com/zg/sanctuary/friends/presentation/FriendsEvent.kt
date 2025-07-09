package com.zg.sanctuary.friends.presentation

sealed interface FriendsEvent {
    data class NavigateToUserProfile(val userId: Int) : FriendsEvent
}