package com.zg.sanctuary

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.zg.sanctuary.auth.presentation.create_account.CreateAccountScreen
import com.zg.sanctuary.auth.presentation.login.LoginScreen
import com.zg.sanctuary.auth.presentation.personal_information.PersonalInformationScreen
import com.zg.sanctuary.core.BeVietnamProTypography
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(
        typography = BeVietnamProTypography()
    ) {
        PersonalInformationScreen(
            onClickNext = {},
            onTapBack = {}
        )
    }
}