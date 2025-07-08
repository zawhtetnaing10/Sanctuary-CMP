package com.zg.sanctuary.home.presentation

import com.zg.sanctuary.auth.domain.User

data class HomeState(
    val loggedInUser: User? = null
)