package com.zg.sanctuary.posts.domain

import com.zg.sanctuary.auth.domain.User
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
    val id : Int,
    @SerialName("content")
    val content : String,
    @SerialName("media_url")
    val mediaUrl : String,
    @SerialName("liked_by_user")
    var likedByUser : Boolean,
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
) {
    @OptIn(ExperimentalTime::class)
    fun formatPostTime(): String{
        val postInstant = try {
            Instant.parse(createdAt)
        } catch (_: IllegalArgumentException) {
            return ""
        }

        val currentInstant = Clock.System.now()

        val duration = currentInstant.minus(postInstant)

        return when {
            duration < 1.minutes -> {
                val seconds = duration.inWholeSeconds
                if (seconds <= 0) "just now" else "$seconds seconds ago"
            }
            duration < 1.hours -> {
                val minutes = duration.inWholeMinutes
                "$minutes minutes ago"
            }
            duration < 1.days -> {
                val hours = duration.inWholeHours
                "$hours hours ago"
            }
            duration < 30.days -> { // Approximately 1 month
                val days = duration.inWholeDays
                "$days days ago"
            }
            duration < 365.days -> { // Approximately 1 year
                val months = (duration.inWholeDays / 30).toInt() // Approximate months
                "$months months ago"
            }
            else -> {
                val years = (duration.inWholeDays / 365).toInt() // Approximate years
                "$years years ago"
            }
        }
    }
}