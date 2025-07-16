package com.zg.sanctuary.chat.presentation

sealed interface ChatEvents {
    data object NavigateBack : ChatEvents
    data object RemoveFocus : ChatEvents
}