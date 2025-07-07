package com.zg.sanctuary.posts.presentation.post_list

sealed interface PostListEvent {
    data class NavigateToPostDetails(val id: Int) : PostListEvent
    class NavigateToCreatePost() : PostListEvent
    data class NavigateToUserProfile(val userId : Int) : PostListEvent
}