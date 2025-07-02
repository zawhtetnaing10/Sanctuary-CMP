package com.zg.sanctuary.posts.presentation.post_details

import com.zg.sanctuary.comments.domain.Comment
import com.zg.sanctuary.posts.domain.Post

data class PostDetailsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val post: Post? = null,
    val comments: List<Comment>? = null,
    val currentComment : String = ""
)