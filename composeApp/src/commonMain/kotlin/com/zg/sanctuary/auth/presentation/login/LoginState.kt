package com.zg.sanctuary.auth.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val error: String = "",
    val isLoading: Boolean = false
)