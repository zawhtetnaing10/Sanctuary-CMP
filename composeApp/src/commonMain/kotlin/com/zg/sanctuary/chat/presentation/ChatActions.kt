package com.zg.sanctuary.chat.presentation

sealed interface ChatActions {
    data object OnErrorDialogDismissed : ChatActions
    data class OnChangeMessage(val message: String) : ChatActions
    data object OnSendMessage : ChatActions
    data object OnTapBack : ChatActions
}