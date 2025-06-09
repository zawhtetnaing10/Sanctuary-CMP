package com.zg.sanctuary.auth.presentation.create_account

import com.zg.sanctuary.AppRoute

sealed interface CreateAccountActions {
    data class OnEmailChanged(val email: String) : CreateAccountActions
    data class OnPasswordChanged(val password: String) : CreateAccountActions
    class OnTapCreateAccount() : CreateAccountActions
    class OnTapBack() : CreateAccountActions
    class OnErrorDialogDismissed() : CreateAccountActions
}