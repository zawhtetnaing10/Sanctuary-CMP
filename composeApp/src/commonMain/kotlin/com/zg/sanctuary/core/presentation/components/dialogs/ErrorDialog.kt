package com.zg.sanctuary.core.presentation.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.DialogProperties
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.TEXT_REGULAR_4X
import com.zg.sanctuary.core.presentation.components.SanctuaryPrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialog(onDismissRequest: () -> Unit, message: String) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        modifier = Modifier.background(
            color = Color.White,
            shape = RoundedCornerShape(MARGIN_MEDIUM)
        )
            .padding(horizontal = MARGIN_LARGE, vertical = MARGIN_XLARGE)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Error", fontWeight = FontWeight.Bold, fontSize = TEXT_REGULAR_4X)
            Spacer(Modifier.height(MARGIN_MEDIUM_2))
            Text(message)
            Spacer(Modifier.height(MARGIN_MEDIUM_2))
            SanctuaryPrimaryButton(
                title = "OK",
                onClick = onDismissRequest,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}