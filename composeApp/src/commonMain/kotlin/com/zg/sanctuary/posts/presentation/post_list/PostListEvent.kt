package com.zg.sanctuary.posts.presentation.post_list

interface PostListEvent {
    data class NavigateToPostDetails(val id: Int) : PostListEvent
    class NavigateToCreatePost() : PostListEvent
}