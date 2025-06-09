package com.zg.sanctuary.auth.presentation.create_account

sealed interface CreateAccountEvent {
    class NavigateToPersonalInformation() : CreateAccountEvent
    class NavigateBack() : CreateAccountEvent
}