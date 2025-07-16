package com.zg.sanctuary

import kotlinx.serialization.Serializable

sealed interface AppRoute {

    // Splash
    @Serializable
    data object Splash : AppRoute

    // Auth
    @Serializable
    data object AuthGraph : AppRoute

    @Serializable
    data object Login : AppRoute

    @Serializable
    data object CreateAccount : AppRoute

    @Serializable
    data object PersonalInformation : AppRoute

    // Home
    @Serializable
    data object Home : AppRoute

    // Post
    @Serializable
    data object PostsGraph : AppRoute

    @Serializable
    data object PostList : AppRoute

    @Serializable
    data class PostDetails(val postId: Int) : AppRoute

    @Serializable
    data object CreatePost : AppRoute

    // Friends
    @Serializable
    data object FriendsGraph : AppRoute

    @Serializable
    data object Friends : AppRoute

    @Serializable
    data class ProfileDetails(val userId: Int) : AppRoute

    // Chat
    @Serializable
    data class Chat(val recipientId: Int) : AppRoute
}