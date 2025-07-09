package com.zg.sanctuary.profile.presentation

sealed interface ProfileActions {
    data object OnTapSendFriendRequest : ProfileActions
    data object OnAcceptFriendRequest : ProfileActions
    data object OnTapLogout : ProfileActions
    data class OnTapPost(val postId: Int) : ProfileActions
    data class OnTapLike(val postId: Int) : ProfileActions
    data class OnTapComment(val postId: Int) : ProfileActions
    class OnErrorDialogDismissed() : ProfileActions
    data object OnTapBack : ProfileActions
}