package com.zg.sanctuary

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.zg.sanctuary.auth.presentation.login.LoginScreen

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onSignUpClicked = {},
        onLoginClicked = {},
    )
}
