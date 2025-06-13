package com.zg.sanctuary.auth.presentation.personal_information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zg.sanctuary.auth.data.repositories.AuthRepository
import com.zg.sanctuary.auth.presentation.personal_information.PersonalInformationEvent.*
import com.zg.sanctuary.interests.data.repositories.InterestRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch

class PersonalInformationViewModel(
    val interestRepo: InterestRepository,
    val authRepo: AuthRepository
) : ViewModel() {
    // State
    private val _state = MutableStateFlow<PersonalInformationState>(PersonalInformationState())
    val state = _state
        .onStart {
            // Get All interests initially
            interestRepo.getAllInterestsFromNetwork(
                onSuccess = { interests ->
                    _state.update { it.copy(interests = interests) }
                },
                onFailure = { errMsg ->
                    _state.update { it.copy(error = errMsg) }
                }
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    // Event
    private val _events = Channel<PersonalInformationEvent>()
    val events = _events.receiveAsFlow()

    // Handle actions
    fun handleAction(action: PersonalInformationActions) {
        when (action) {
            is PersonalInformationActions.OnChangeDob -> {
                _state.update { it.copy(dob = action.dob) }
            }

            is PersonalInformationActions.OnChangeFullName -> {
                _state.update { it.copy(fullName = action.fullName) }
            }

            is PersonalInformationActions.OnChangeUserName -> {
                _state.update { it.copy(userName = action.userName) }
            }

            is PersonalInformationActions.OnInterestPicked -> {
                val tmpInterestList = _state.value.chosenInterests.toMutableSet()

                if (tmpInterestList.contains(action.interest)) {
                    tmpInterestList.remove(action.interest)
                } else {
                    tmpInterestList.add(action.interest)
                }

                _state.update { it.copy(chosenInterests = tmpInterestList) }
            }

            is PersonalInformationActions.OnPickedImage -> {
                _state.update { it.copy(pickedImage = action.image) }
            }

            is PersonalInformationActions.OnCompleteSignUpTapped -> {
                if(_state.value.pickedImage.isEmpty()){
                    _state.update { it.copy(error = "Please take a picture from camera or pick an image from gallery for your profile pic.") }
                    return
                }

                if(_state.value.fullName.isEmpty()){
                    _state.update { it.copy(error = "Full name cannot be empty.") }
                    return
                }

                if(_state.value.userName.isEmpty()){
                    _state.update { it.copy(error = "Username cannot be empty.") }
                    return
                }

                if(_state.value.dob.isEmpty()){
                    _state.update { it.copy(error = "Date of birth cannot be empty.") }
                    return
                }

                if(_state.value.chosenInterests.isEmpty()){
                    _state.update { it.copy(error = "Please choose your interests.") }
                    return
                }

                _state.update { it.copy(isLoading = true) }

                viewModelScope.launch {
                    authRepo.updateUser(
                        profileImage = _state.value.pickedImage,
                        fullName = _state.value.fullName,
                        userName = _state.value.userName,
                        dob = _state.value.dob,
                        chosenInterests = _state.value.chosenInterests.toList(),
                        onSuccess = {
                            _state.update { it.copy(isLoading = false) }
                            viewModelScope.launch {
                                _events.send(NavigateToHome())
                            }
                        },
                        onFailure = { errMsg ->
                            _state.update { it.copy(isLoading = false, error = errMsg) }
                        }
                    )
                }
            }

            is PersonalInformationActions.OnTapBack -> {
                viewModelScope.launch {
                    _events.send(NavigateBack())
                }
            }

            is PersonalInformationActions.OnErrorDialogDismissed -> {
                _state.update { it.copy(error = "") }
            }
        }
    }
}