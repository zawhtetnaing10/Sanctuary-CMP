package com.zg.sanctuary.friends.presentation

sealed interface FriendsAction {
    data class OnAcceptFriendRequest(val frId: Int) : FriendsAction
    data object OnErrorDialogDismissed : FriendsAction
    data class OnTapProfile(val userId: Int) : FriendsAction
}