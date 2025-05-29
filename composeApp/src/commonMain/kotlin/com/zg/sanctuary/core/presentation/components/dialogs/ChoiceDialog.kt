package com.zg.sanctuary.core.presentation.components.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.presentation.components.SanctuaryPrimaryButtonWithIcon
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.sanctuary_logo

@Composable
fun ChoiceDialog(
    onDismissRequest: () -> Unit,
    message: String,
    rightButtonLabel: String,
    rightButtonIcon: Painter,
    onTapRightButton: () -> Unit,
    leftButtonLabel: String,
    leftButtonIcon: Painter,
    onTapLeftButton: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(MARGIN_MEDIUM),
            modifier = Modifier.padding(horizontal = MARGIN_LARGE)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(MARGIN_MEDIUM_2)) {
                // Logo
                Image(
                    painterResource(Res.drawable.sanctuary_logo),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )

                // Message
                Text(message, textAlign = TextAlign.Center, modifier = Modifier.padding(top = MARGIN_LARGE))

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MARGIN_CARD_MEDIUM_2),
                    modifier = Modifier.padding(top = MARGIN_LARGE)
                ) {
                    // Left Button
                    SanctuaryPrimaryButtonWithIcon(
                        label = leftButtonLabel,
                        icon = leftButtonIcon,
                        onTapButton = onTapLeftButton,
                        modifier = Modifier.weight(1f)
                    )
                    // Right Button
                    SanctuaryPrimaryButtonWithIcon(
                        label = rightButtonLabel,
                        icon = rightButtonIcon,
                        onTapButton = onTapRightButton,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}