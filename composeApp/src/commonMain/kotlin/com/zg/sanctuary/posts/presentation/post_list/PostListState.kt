package com.zg.sanctuary.posts.presentation.post_list

import com.zg.sanctuary.posts.data.network.responses.Meta
import com.zg.sanctuary.posts.domain.Post

data class PostListState(
    val isLoading: Boolean = false,
    val error: String = "",
    val posts: List<Post> = listOf(),
    val meta: Meta? = null,
    val isLoadingMore: Boolean = false
)