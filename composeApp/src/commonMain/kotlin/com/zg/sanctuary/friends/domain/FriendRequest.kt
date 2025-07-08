package com.zg.sanctuary.friends.domain

import com.zg.sanctuary.auth.domain.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendRequest(
    @SerialName("id")
    val id: Int,
    @SerialName("sender_id")
    val senderId: Int,
    @SerialName("receiver_id")
    val receiverId: Int,
    @SerialName("request_status")
    val requestStatus: String,
    @SerialName("requested_at")
    val requestedAt: String,
    @SerialName("accepted_at")
    val acceptedAt: String? = null,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("user")
    val user: User? = null
)
