package com.zg.sanctuary.posts.presentation.post_details

import com.zg.sanctuary.chat.presentation.ChatEvents

sealed interface PostDetailsEvent {
    data object NavigateBack : PostDetailsEvent
    data object RemoveFocus : PostDetailsEvent
    data class NavigateToUserProfile(val userId : Int) : PostDetailsEvent
    data class ScrollToBottom(val lastIndex: Int) : PostDetailsEvent
}