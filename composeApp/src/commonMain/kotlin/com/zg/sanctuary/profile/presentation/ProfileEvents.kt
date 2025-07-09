package com.zg.sanctuary.profile.presentation

sealed interface ProfileEvents {
    data object NavigateBack : ProfileEvents
    data class NavigateToPostDetails(val postId : Int) : ProfileEvents
    data object NavigateBackToLogin : ProfileEvents
}