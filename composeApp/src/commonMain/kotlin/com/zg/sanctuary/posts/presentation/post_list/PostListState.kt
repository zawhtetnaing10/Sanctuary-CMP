package com.zg.sanctuary.posts.presentation.post_list

import com.zg.sanctuary.posts.domain.Post

data class PostListState(
    val posts: List<Post> = listOf()
)