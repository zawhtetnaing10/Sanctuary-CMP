package com.zg.sanctuary

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.zg.sanctuary.auth.presentation.create_account.CreateAccountScreen

@Preview(showBackground = true)
@Composable
fun CreateAccountScreenPreview() {
    CreateAccountScreen(
        onClickSignUp = {},
    )
}
