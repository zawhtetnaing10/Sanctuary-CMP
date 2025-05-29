package com.zg.sanctuary.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_XXLARGE
import com.zg.sanctuary.core.PRIMARY_COLOR
import com.zg.sanctuary.core.TEXT_REGULAR_2X

@Composable
fun SanctuaryPrimaryButtonWithIcon(
    label : String,
    icon : Painter,
    onTapButton: () -> Unit,
    modifier : Modifier = Modifier
) {
    Button(
        onClick = onTapButton,
        shape = RoundedCornerShape(MARGIN_LARGE),
        colors = ButtonDefaults.buttonColors(
            containerColor = PRIMARY_COLOR
        ),
        modifier = modifier.height(MARGIN_XXLARGE)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(MARGIN_LARGE)
            )
            Text(
                label,
                fontSize = TEXT_REGULAR_2X,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = MARGIN_MEDIUM)
            )
        }
    }
}