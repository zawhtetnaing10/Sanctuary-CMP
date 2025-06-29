package com.zg.sanctuary.posts.domain

import com.zg.sanctuary.auth.domain.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    @SerialName("id")
    val id : Int,
    @SerialName("content")
    val content : String,
    @SerialName("media_url")
    val mediaUrl : String,
    @SerialName("liked_by_user")
    val likedByUser : Boolean,
    @SerialName("like_count")
    val likeCount : Int,
    @SerialName("comment_count")
    val commentCount : Int,
    @SerialName("created_at")
    val createdAt : String,
    @SerialName("updated_at")
    val updatedAt : String,
    @SerialName("user")
    val user: User
)