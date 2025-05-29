package com.zg.sanctuary.auth.presentation.personal_information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
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
import com.zg.sanctuary.core.presentation.components.SanctuaryPrimaryButton
import com.zg.sanctuary.core.presentation.components.SanctuaryTextField
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
    onClickNext: () -> Unit,
    onTapBack: () -> Unit
) {
    PersonalInformationScreen(
        onClickNext = {
            onClickNext()
        },
        onTapBack = {
            onTapBack()
        }
    )
}

@Composable
fun PersonalInformationScreen(
    onClickNext: () -> Unit,
    onTapBack: () -> Unit
) {

    val scrollState = rememberScrollState()

    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            CommonAppbar(title = stringResource(Res.string.title_profile_information), onTapBack = { onTapBack() })
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(horizontal = MARGIN_MEDIUM_3)
                .pointerInput (Unit) { // 'Unit' as key means this will not recompose unless the lambda changes
                    detectTapGestures(onTap = {
                        focusManager.clearFocus() // 3. Clear focus on any tap outside
                    })
                }
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Profile Picture
                ProfilePicture(modifier = Modifier)
                // Full Name
                SanctuaryTextField(
                    inputText = "",
                    onInputChanged = {},
                    hint = stringResource(Res.string.full_name_hint),
                    modifier = Modifier.padding(top = MARGIN_LARGE)
                )
                // Username
                SanctuaryTextField(
                    inputText = "",
                    onInputChanged = {},
                    hint = stringResource(Res.string.user_name_hint),
                    modifier = Modifier.padding(top = MARGIN_LARGE)
                )
                // Date of Birth
                SanctuaryTextField(
                    inputText = "",
                    onInputChanged = {},
                    hint = stringResource(Res.string.date_of_birth),
                    isEnabled = false,
                    modifier = Modifier.padding(top = MARGIN_LARGE)
                        .clickable {
                            // TODO: - Open date picker here
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

                InterestsSelection(modifier = Modifier.padding(top = MARGIN_MEDIUM_2))
            }

            // Complete Sign up
            SanctuaryPrimaryButton(
                title = stringResource(Res.string.complete_sign_up),
                onClick = {
                    // TODO: - Communicate with view model
                    onClickNext()
                },
                modifier = Modifier.fillMaxWidth().padding(top = MARGIN_56, bottom = MARGIN_XXLARGE)
            )
        }
    }
}