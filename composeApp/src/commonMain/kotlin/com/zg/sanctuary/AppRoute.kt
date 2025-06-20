package com.zg.sanctuary

import kotlinx.serialization.Serializable

sealed interface AppRoute {
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

    // Friends
    @Serializable
    data object FriendsGraph : AppRoute

    @Serializable
    data object Friends : AppRoute

    // Profile
    @Serializable
    data object ProfileGraph : AppRoute

    @Serializable
    data object Profile : AppRoute

}