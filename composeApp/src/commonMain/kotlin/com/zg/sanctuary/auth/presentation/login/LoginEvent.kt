package com.zg.sanctuary.auth.presentation.login

sealed interface LoginEvent {
    class NavigateToHome() : LoginEvent
    class NavigateToSignUp() : LoginEvent
}