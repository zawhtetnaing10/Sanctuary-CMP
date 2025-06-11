package com.zg.sanctuary.auth.presentation.personal_information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zg.sanctuary.auth.presentation.login.LoginActions
import com.zg.sanctuary.core.MARGIN_56
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_3
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.MARGIN_XXLARGE
import com.zg.sanctuary.core.TEXT_REGULAR_4X
import com.zg.sanctuary.core.presentation.components.CommonAppbar
import com.zg.sanctuary.core.presentation.components.InterestsSelection
import com.zg.sanctuary.core.presentation.components.ProfilePicture
import com.zg.sanctuary.core.presentation.components.SanctuaryDatePicker
import com.zg.sanctuary.core.presentation.components.SanctuaryPrimaryButton
import com.zg.sanctuary.core.presentation.components.SanctuaryTextField
import com.zg.sanctuary.core.presentation.components.dialogs.ErrorDialog
import com.zg.sanctuary.core.presentation.components.dialogs.LoadingDialog
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.complete_sign_up
import sanctuary.composeapp.generated.resources.date_of_birth
import sanctuary.composeapp.generated.resources.full_name_hint
import sanctuary.composeapp.generated.resources.interests
import sanctuary.composeapp.generated.resources.title_profile_information
import sanctuary.composeapp.generated.resources.user_name_hint

@Composable
fun PersonalInformationRoute(
    viewModel: PersonalInformationViewModel,
    onNavigateToHomeTriggered: () -> Unit,
    onBackTriggered: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is PersonalInformationEvent.NavigateBack -> {
                    onBackTriggered()
                }

                is PersonalInformationEvent.NavigateToHome -> {
                    onNavigateToHomeTriggered()
                }
            }
        }
    }

    PersonalInformationScreen(
        state = state,
        onAction = {
            viewModel.handleAction(it)
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PersonalInformationScreen(
    state: PersonalInformationState,
    onAction: (PersonalInformationActions) -> Unit,
) {

    val scrollState = rememberScrollState()

    val focusManager = LocalFocusManager.current

    if (state.isLoading) {
        LoadingDialog(onDismissRequest = {})
    }

    if (state.error.isNotEmpty()) {
        ErrorDialog(
            onDismissRequest = {
                onAction(PersonalInformationActions.OnErrorDialogDismissed())
            },
            message = state.error
        )
    }

    Scaffold(
        topBar = {
            CommonAppbar(
                title = stringResource(Res.string.title_profile_information),
                onTapBack = { onAction(PersonalInformationActions.OnTapBack()) })
        },
        containerColor = Color.White
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(horizontal = MARGIN_MEDIUM_3)
                .pointerInput(Unit) { // 'Unit' as key means this will not recompose unless the lambda changes
                    detectTapGestures(onTap = {
                        focusManager.clearFocus() // 3. Clear focus on any tap outside
                    })
                }
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Profile Picture
                ProfilePicture(onImagePicked = { byteArray ->
                    onAction(PersonalInformationActions.OnPickedImage(byteArray))
                }, modifier = Modifier)
                // Full Name
                SanctuaryTextField(
                    inputText = state.fullName,
                    onInputChanged = {
                        onAction(PersonalInformationActions.OnChangeFullName(it))
                    },
                    hint = stringResource(Res.string.full_name_hint),
                    modifier = Modifier.padding(top = MARGIN_LARGE)
                )
                // Username
                SanctuaryTextField(
                    inputText = state.userName,
                    onInputChanged = {
                        onAction(PersonalInformationActions.OnChangeUserName(it))
                    },
                    hint = stringResource(Res.string.user_name_hint),
                    modifier = Modifier.padding(top = MARGIN_LARGE)
                )
                // Dob
                SanctuaryDatePicker(
                    dob = state.dob,
                    onDobChanged = {
                        onAction(PersonalInformationActions.OnChangeDob(it))
                    }
                )
            }

            // Interests
            Column(modifier = Modifier.padding(top = MARGIN_XLARGE)) {
                Text(
                    stringResource(Res.string.interests),
                    fontSize = TEXT_REGULAR_4X,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = MARGIN_SMALL)
                )

                InterestsSelection(
                    interests = state.interests,
                    chosenInterests = state.chosenInterests,
                    onInterestPicked = {
                        onAction(PersonalInformationActions.OnInterestPicked(it))
                    },
                    modifier = Modifier.padding(top = MARGIN_MEDIUM_2)
                )
            }

            // Complete Sign up
            SanctuaryPrimaryButton(
                title = stringResource(Res.string.complete_sign_up),
                onClick = {
                    onAction(PersonalInformationActions.OnCompleteSignUpTapped())
                },
                modifier = Modifier.fillMaxWidth().padding(top = MARGIN_56, bottom = MARGIN_XXLARGE)
            )
        }
    }
}