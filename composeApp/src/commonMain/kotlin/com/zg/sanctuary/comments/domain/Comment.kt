package com.zg.sanctuary.comments.domain

import com.zg.sanctuary.auth.domain.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    @SerialName("id")
    val id: Int,
    @SerialName("content")
    val content: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("post_id")
    val postId : Int,
    @SerialName("user_id")
    val userId : Int,
    @SerialName("user")
    val user : User
)