package com.zg.sanctuary.auth.presentation.create_account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_3
import com.zg.sanctuary.core.NORMAL_LABEL_COLOR
import com.zg.sanctuary.core.TEXT_REGULAR
import com.zg.sanctuary.core.presentation.components.CommonAppbar
import com.zg.sanctuary.core.presentation.components.SanctuaryPasswordTextField
import com.zg.sanctuary.core.presentation.components.SanctuaryPrimaryButton
import com.zg.sanctuary.core.presentation.components.SanctuaryTextField
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.email_hint
import sanctuary.composeapp.generated.resources.password
import sanctuary.composeapp.generated.resources.signup
import sanctuary.composeapp.generated.resources.terms_of_service_sign_up
import sanctuary.composeapp.generated.resources.title_create_account

@Composable
fun CreateAccountRoute(
    onClickBack: () -> Unit,
    onClickSignUp: () -> Unit
) {
    CreateAccountScreen(
        onClickSignUp = {
            onClickSignUp()
        },
        onClickBack = {
            onClickBack()
        }
    )
}

@Composable
fun CreateAccountScreen(
    onClickBack: () -> Unit,
    onClickSignUp: () -> Unit
) {
    Scaffold(
        topBar = {
            CommonAppbar(title = stringResource(Res.string.title_create_account), onTapBack = {
                onClickBack()
            })
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(horizontal = MARGIN_MEDIUM_3)) {
            // Email
            SanctuaryTextField(
                inputText = "",
                onInputChanged = {},
                hint = stringResource(Res.string.email_hint),
                modifier = Modifier.padding(top = MARGIN_MEDIUM_2)
            )
            // Password
            SanctuaryPasswordTextField(
                inputText = "",
                onInputChanged = {},
                hint = stringResource(Res.string.password),
                modifier = Modifier.padding(top = MARGIN_LARGE)
            )
            // Sign up button
            SanctuaryPrimaryButton(
                title = stringResource(Res.string.signup),
                onClick = {
                    onClickSignUp()
                },
                modifier = Modifier.padding(top = MARGIN_LARGE).fillMaxWidth()
            )
            // Terms of service
            Text(
                stringResource(Res.string.terms_of_service_sign_up),
                textAlign = TextAlign.Center,
                fontSize = TEXT_REGULAR,
                color = NORMAL_LABEL_COLOR,
                modifier = Modifier.padding(top = MARGIN_LARGE)
            )
        }
    }
}