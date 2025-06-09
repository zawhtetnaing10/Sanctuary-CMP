package com.zg.sanctuary.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    // State
    private val _state = MutableStateFlow<LoginState>(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    // Events
    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    fun onEmailChanged(email: String) {
        _state.update {
            it.copy(email = email)
        }
    }

    fun onPasswordChanged(password: String) {
        _state.update {
            it.copy(password = password)
        }
    }

    fun onErrorDialogDismissed() {
        _state.update {
            it.copy(error = "")
        }
    }

    fun onLoginTapped() {
        // TODO: - Make login network call and Navigate to Home Screen
        println("Login button tapped. Communicate with AuthRepository")

        viewModelScope.launch {
            // TODO: - Delete loadings after testing
            _state.update {
                it.copy(isLoading = true)
            }
            delay(1000) // TODO:- replace with network call here.
            _state.update {
                it.copy(isLoading = false)
            }

            // TODO: - Delete this after testing
            _state.update {
                it.copy(error = "Login failed")
            }
            _events.send(LoginEvent.NavigateToHome())
        }
    }

    fun onSignUpTapped() {
        viewModelScope.launch {
            _events.send(LoginEvent.NavigateToSignUp())
        }
    }
}

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
)

sealed interface LoginEvent {
    class NavigateToHome() : LoginEvent
    class NavigateToSignUp() : LoginEvent
}