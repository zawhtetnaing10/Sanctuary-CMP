package com.zg.sanctuary

import kotlinx.serialization.Serializable

sealed interface AppRoute {
    @Serializable
    data object AuthGraph : AppRoute

    @Serializable
    data object Login : AppRoute

    @Serializable
    data object CreateAccount : AppRoute

    @Serializable
    data object PersonalInformation : AppRoute

}