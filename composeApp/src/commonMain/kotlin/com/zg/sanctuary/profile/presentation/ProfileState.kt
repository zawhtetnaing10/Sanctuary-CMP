package com.zg.sanctuary.profile.presentation

import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.posts.domain.Post
import com.zg.sanctuary.profile.domain.UserProfileDetails

data class ProfileState(
    val error: String = "",
    val isLoading: Boolean = false,
    val loggedInUser: User? = null,
    val userProfileDetails: UserProfileDetails? = null,
    val userStatus: UserStatus = UserStatus.LOGGED_IN_USER,
    val postsByUser: List<Post> = listOf()
)

enum class UserStatus {
    FRIEND_REQUEST_PENDING,
    RECEIVED_FRIEND_REQUEST,
    FRIENDS,
    LOGGED_IN_USER,
    ABLE_TO_SEND_FRIEND_REQUEST
}