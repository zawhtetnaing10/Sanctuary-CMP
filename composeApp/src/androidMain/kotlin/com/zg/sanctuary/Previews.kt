package com.zg.sanctuary

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.zg.sanctuary.core.presentation.components.SanctuaryPasswordTextField
import com.zg.sanctuary.core.presentation.components.SanctuaryTextField

@Preview(heightDp = 200)
@Composable
fun SanctuaryPasswordTextFieldPreview(){
    Box{
        SanctuaryPasswordTextField(
            inputText = "Password",
            onInputChanged = {

            },
            hint = "Password"
        )
    }
}
