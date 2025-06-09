package com.zg.sanctuary.auth.presentation.create_account

data class CreateAccountState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
)