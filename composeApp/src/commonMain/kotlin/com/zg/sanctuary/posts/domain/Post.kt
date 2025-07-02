package com.zg.sanctuary.posts.domain

import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.core.utils.DateUtils
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

@Serializable
data class Post(
    @SerialName("id")
    val id: Int,
    @SerialName("content")
    val content: String,
    @SerialName("media_url")
    val mediaUrl: String,
    @SerialName("liked_by_user")
    var likedByUser: Boolean,
    @SerialName("like_count")
    val likeCount: Int,
    @SerialName("comment_count")
    val commentCount: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("user")
    val user: User
) {
    fun formatPostTime(): String {
        return DateUtils.formatTimeAgo(createdAt)
    }
}