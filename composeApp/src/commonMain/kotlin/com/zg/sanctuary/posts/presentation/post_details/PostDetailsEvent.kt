package com.zg.sanctuary.posts.presentation.post_details

sealed interface PostDetailsEvent {
    data object NavigateBack : PostDetailsEvent
    data object RemoveFocus : PostDetailsEvent
    data class NavigateToUserProfile(val userId : Int) : PostDetailsEvent
}