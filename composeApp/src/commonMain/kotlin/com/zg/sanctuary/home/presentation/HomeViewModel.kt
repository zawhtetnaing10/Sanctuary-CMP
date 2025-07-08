package com.zg.sanctuary.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zg.sanctuary.auth.data.repositories.AuthRepository
import com.zg.sanctuary.posts.presentation.post_details.PostDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class HomeViewModel(
    val authRepo: AuthRepository
) : ViewModel() {
    // state
    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {

            // Get Logged In User
            val loggedInUser = authRepo.getLoggedInUser()
            _state.update {
                it.copy(loggedInUser = loggedInUser)
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )
}