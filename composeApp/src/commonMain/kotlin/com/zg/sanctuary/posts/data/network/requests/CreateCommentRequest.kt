package com.zg.sanctuary.posts.data.network.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateCommentRequest(
    @SerialName("content")
    val content : String,
    @SerialName("post_id")
    val postId : Int
)