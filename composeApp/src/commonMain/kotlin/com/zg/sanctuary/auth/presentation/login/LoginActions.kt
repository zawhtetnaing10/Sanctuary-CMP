package com.zg.sanctuary.auth.presentation.login

sealed interface LoginActions {
    data class OnEmailChanged(val email: String) : LoginActions
    data class OnPasswordChanged(val password: String) : LoginActions
    class OnLoginTapped() : LoginActions
    class OnErrorDialogDismissed() : LoginActions
    class OnSignUpTapped() : LoginActions
}