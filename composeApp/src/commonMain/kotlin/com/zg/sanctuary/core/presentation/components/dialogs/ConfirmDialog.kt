package com.zg.sanctuary.core.presentation.components.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.sanctuary_logo

// TODO: - Implement later
@Composable
fun ConfirmDialog(
    onDismissRequest: () -> Unit,
    message: String,
    confirmButtonLabel: String,
    cancelButtonLabel: String,
    onChooseConfirm: () -> Unit,
    onChooseCancel: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(modifier = Modifier.padding(MARGIN_MEDIUM_2)) {
            Column {
                // Logo
                Image(
                    painterResource(Res.drawable.sanctuary_logo),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )

                // Message
                Text(message, textAlign = TextAlign.Center, modifier = Modifier.padding(top = MARGIN_MEDIUM_2))

                // Buttons
                Row {
                    // Confirm Button

                }
            }
        }
    }
}