package com.zg.sanctuary.auth.presentation.create_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zg.sanctuary.auth.data.repositories.AuthRepository
import com.zg.sanctuary.auth.presentation.create_account.CreateAccountEvent.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Create Account View Model
class CreateAccountViewModel(
    private val authRepo: AuthRepository
) : ViewModel() {
    // State
    private val _state = MutableStateFlow<CreateAccountState>(CreateAccountState())
    val state = _state.asStateFlow()

    // Event
    private val _events = Channel<CreateAccountEvent>()
    val events = _events.receiveAsFlow()

    fun handleAction(action: CreateAccountActions) {
        when (action) {
            is CreateAccountActions.OnEmailChanged -> {
                _state.update {
                    it.copy(email = action.email)
                }
            }

            is CreateAccountActions.OnPasswordChanged -> {
                _state.update {
                    it.copy(password = action.password)
                }
            }

            is CreateAccountActions.OnTapCreateAccount -> {
                _state.update { it.copy(isLoading = true) }

                viewModelScope.launch {
                    authRepo.createAccount(
                        email = _state.value.email,
                        password = _state.value.password,
                        onSuccess = {
                            _state.update { it.copy(isLoading = false) }
                            viewModelScope.launch {
                                _events.send(NavigateToPersonalInformation())
                            }
                        },
                        onFailure = { errorMsg ->
                            _state.update { it.copy(isLoading = false, error = errorMsg) }
                        }
                    )
                }

            }

            is CreateAccountActions.OnTapBack -> {
                viewModelScope.launch {
                    _events.send(NavigateBack())
                }
            }

            is CreateAccountActions.OnErrorDialogDismissed -> {
                _state.update { it.copy(error = "") }
            }
        }
    }
}