package com.zg.sanctuary.profile.domain

import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.friends.domain.FriendRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDetails(
    @SerialName("user")
    val user: User,
    @SerialName("friend_requests")
    val friendRequests: List<FriendRequest>
)