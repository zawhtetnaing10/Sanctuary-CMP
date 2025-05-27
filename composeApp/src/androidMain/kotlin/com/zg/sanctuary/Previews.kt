package com.zg.sanctuary

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.zg.sanctuary.auth.presentation.personal_information.PersonalInformationScreen

@Preview(showBackground = true, backgroundColor = 0xFF000000,)
@Composable
fun PersonalInformationScreenPreview() {
    PersonalInformationScreen(
        onClickNext = {},
        onTapBack = {}
    )
}
