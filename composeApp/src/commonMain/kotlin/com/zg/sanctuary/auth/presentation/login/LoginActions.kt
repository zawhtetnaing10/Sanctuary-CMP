package com.zg.sanctuary.auth.presentation.login

sealed interface LoginActions {
    data class OnEmailChanged(val email: String) : LoginActions
    data class OnPasswordChanged(val password: String) : LoginActions
    object OnLoginTapped : LoginActions
    object OnErrorDialogDismissed : LoginActions
    object OnSignUpTapped : LoginActions
}