package com.zg.sanctuary.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.zg.sanctuary.core.HINT_COLOR
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.TEXT_FIELD_BACKGROUND_COLOR
import com.zg.sanctuary.core.TEXT_REGULAR_2X
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.hide
import sanctuary.composeapp.generated.resources.show

@Composable
fun SanctuaryPasswordTextField(
    inputText: String,
    onInputChanged: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier
) {

    var isPasswordHidden by remember { mutableStateOf(true) }

    OutlinedTextField(
        inputText,
        onValueChange = onInputChanged,
        placeholder = {
            Text(hint, color = HINT_COLOR, fontSize = TEXT_REGULAR_2X)
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = TEXT_FIELD_BACKGROUND_COLOR,
            unfocusedContainerColor = TEXT_FIELD_BACKGROUND_COLOR,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        visualTransformation = if(isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        suffix = {
            val suffix = if(isPasswordHidden) stringResource(Res.string.show) else stringResource(Res.string.hide)

            Text(
                suffix,
                color = HINT_COLOR,
                fontSize = TEXT_REGULAR_2X,
                modifier = Modifier.clickable {
                    // Change password visibility
                    isPasswordHidden = !isPasswordHidden
                }
            )
        },
        shape = RoundedCornerShape(MARGIN_CARD_MEDIUM_2),
        modifier = modifier.fillMaxWidth()
            .height(56.dp)
    )
}