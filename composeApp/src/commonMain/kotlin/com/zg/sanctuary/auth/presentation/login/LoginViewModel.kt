package com.zg.sanctuary.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zg.sanctuary.auth.data.repositories.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepo : AuthRepository
) : ViewModel() {

    // State
    private val _state = MutableStateFlow<LoginState>(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    // Events
    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    fun handleAction(action : LoginActions){
        when(action){
            is LoginActions.OnEmailChanged -> {
                _state.update {
                    it.copy(email = action.email)
                }
            }

            is LoginActions.OnPasswordChanged -> {
                _state.update {
                    it.copy(password = action.password)
                }
            }

            is LoginActions.OnErrorDialogDismissed -> {
                _state.update {
                    it.copy(error = "")
                }
            }

            is LoginActions.OnLoginTapped -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(isLoading = true)
                    }
                    authRepo.login(
                        email = _state.value.email,
                        password = _state.value.password,
                        onSuccess = {
                            _state.update { it.copy(isLoading = false) }
                            // Login successful navigate to home
                            viewModelScope.launch {
                                _events.send(LoginEvent.NavigateToHome())
                            }
                        },
                        onFailure = { errorMsg ->
                            // Login failed. Show error message
                            _state.update { it.copy(isLoading = false, error = errorMsg) }
                        }
                    )
                }
            }

            is LoginActions.OnSignUpTapped -> {
                viewModelScope.launch {
                    _events.send(LoginEvent.NavigateToSignUp())
                }
            }
        }
    }
}