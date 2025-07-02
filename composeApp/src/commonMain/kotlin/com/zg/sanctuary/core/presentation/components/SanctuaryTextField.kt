package com.zg.sanctuary.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zg.sanctuary.core.HINT_COLOR
import com.zg.sanctuary.core.MARGIN_56
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.TEXT_FIELD_BACKGROUND_COLOR
import com.zg.sanctuary.core.TEXT_REGULAR_2X
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SanctuaryTextField(
    inputText: String,
    onInputChanged: (String) -> Unit,
    hint: String,
    fillMaxWidth: Boolean = true,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        inputText,
        onValueChange = onInputChanged,
        placeholder = {
            Text(hint, color = HINT_COLOR, fontSize = TEXT_REGULAR_2X)
        },
        enabled = isEnabled,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = TEXT_FIELD_BACKGROUND_COLOR,
            unfocusedContainerColor = TEXT_FIELD_BACKGROUND_COLOR,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledContainerColor = TEXT_FIELD_BACKGROUND_COLOR,
            disabledBorderColor = Color.Transparent,
            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            disabledTextColor = Color.Black
        ),
        shape = RoundedCornerShape(MARGIN_CARD_MEDIUM_2),
        modifier =
            if (fillMaxWidth)
                modifier.fillMaxWidth().height(MARGIN_56)
            else modifier.height(MARGIN_56)
    )
}

@Preview()
@Composable
fun SanctuaryTextFieldPreview() {
    SanctuaryTextField(
        inputText = "",
        onInputChanged = {},
        hint = "",
        isEnabled = false,
    )
}