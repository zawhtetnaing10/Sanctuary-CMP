package com.zg.sanctuary.auth.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zg.sanctuary.core.LOGO_BOTTOM_SPACING
import com.zg.sanctuary.core.LOGO_HEIGHT
import com.zg.sanctuary.core.LOGO_WIDTH
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM_3
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.MARGIN_XXLARGE
import com.zg.sanctuary.core.NORMAL_LABEL_COLOR
import com.zg.sanctuary.core.TEXT_REGULAR
import com.zg.sanctuary.core.presentation.components.SanctuaryAccentButton
import com.zg.sanctuary.core.presentation.components.SanctuaryPasswordTextField
import com.zg.sanctuary.core.presentation.components.SanctuaryPrimaryButton
import com.zg.sanctuary.core.presentation.components.SanctuaryTextField
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.login
import sanctuary.composeapp.generated.resources.no_account_label
import sanctuary.composeapp.generated.resources.password
import sanctuary.composeapp.generated.resources.sanctuary_logo
import sanctuary.composeapp.generated.resources.signup
import sanctuary.composeapp.generated.resources.terms_of_service_login
import sanctuary.composeapp.generated.resources.username_or_email_hint
import androidx.compose.runtime.getValue
import com.zg.sanctuary.core.presentation.components.dialogs.ErrorDialog
import com.zg.sanctuary.core.presentation.components.dialogs.LoadingDialog
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    viewModel: LoginViewModel,
    onNavigateToSignUpTriggered: () -> Unit,
    onNavToHomeTriggered: () -> Unit
) {
    val loginState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        // Observe events here.
        viewModel.events.collectLatest {
            when (it) {
                is LoginEvent.NavigateToHome -> {
                    println("Navigate to home right now. From View Model")
                    onNavToHomeTriggered()
                }

                is LoginEvent.NavigateToSignUp -> {
                    onNavigateToSignUpTriggered()
                }
            }
        }
    }

    LoginScreen(
        state = loginState,
        onSignUpClicked = {
            viewModel.onSignUpTapped()
        },
        onLoginClicked = {
            viewModel.onLoginTapped()
        },
        onEmailChanged = {
            viewModel.onEmailChanged(it)
        },
        onPasswordChanged = {
            viewModel.onPasswordChanged(it)
        },
        onErrorDialogDismissed = {
            viewModel.onErrorDialogDismissed()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state: LoginState,
    onSignUpClicked: () -> Unit,
    onLoginClicked: () -> Unit,
    onErrorDialogDismissed: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
) {

    val scrollSate = rememberScrollState()

    if (state.isLoading) {
        // TODO: - Extract it to a separate composable
        LoadingDialog(onDismissRequest = {})
    }

    if (state.error.isNotEmpty()) {
        ErrorDialog(
            onDismissRequest = onErrorDialogDismissed,
            message = state.error
        )
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(scrollSate).padding(horizontal = MARGIN_MEDIUM_3)
        ) {
            // Logo
            Image(
                painterResource(Res.drawable.sanctuary_logo),
                contentDescription = null,
                modifier = Modifier.width(LOGO_WIDTH).height(LOGO_HEIGHT)
                    .padding(top = MARGIN_XLARGE)
            )

            // User name or email
            SanctuaryTextField(
                inputText = state.email,
                onInputChanged = onEmailChanged,
                hint = stringResource(Res.string.username_or_email_hint),
                modifier = Modifier.padding(top = LOGO_BOTTOM_SPACING)
            )

            // Password
            SanctuaryPasswordTextField(
                inputText = state.password,
                onInputChanged = onPasswordChanged,
                hint = stringResource(Res.string.password),
                modifier = Modifier.padding(top = MARGIN_LARGE)
            )

            // Login
            SanctuaryPrimaryButton(
                title = stringResource(Res.string.login),
                onClick = {
                    onLoginClicked()
                },
                modifier = Modifier.padding(top = MARGIN_LARGE).fillMaxWidth()
            )

            // Don't have an account?
            Text(
                stringResource(Res.string.no_account_label),
                fontSize = TEXT_REGULAR,
                color = NORMAL_LABEL_COLOR,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = MARGIN_LARGE)
            )

            // Signup
            SanctuaryAccentButton(
                title = stringResource(Res.string.signup),
                onClick = {
                    onSignUpClicked()
                },
                modifier = Modifier.padding(top = MARGIN_LARGE).fillMaxWidth()
            )

            // Terms of service
            Text(
                stringResource(Res.string.terms_of_service_login),
                fontSize = TEXT_REGULAR,
                color = NORMAL_LABEL_COLOR,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = MARGIN_LARGE, bottom = MARGIN_XXLARGE)
            )
        }
    }
}