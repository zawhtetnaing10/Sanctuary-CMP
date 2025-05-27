package com.zg.sanctuary.core.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.zg.sanctuary.core.ACCENT_COLOR
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_XXLARGE
import com.zg.sanctuary.core.TEXT_REGULAR_2X

@Composable
fun SanctuaryAccentButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(MARGIN_LARGE),
        colors = ButtonDefaults.buttonColors(
            containerColor = ACCENT_COLOR
        ),
        modifier = modifier.height(MARGIN_XXLARGE)
    ) {
        Text(title, fontSize = TEXT_REGULAR_2X, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}