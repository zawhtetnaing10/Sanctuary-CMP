package com.zg.sanctuary.auth.presentation.personal_information

sealed interface PersonalInformationEvent {
    class NavigateToHome() : PersonalInformationEvent
    class NavigateBack() : PersonalInformationEvent
}