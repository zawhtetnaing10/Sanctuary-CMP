package com.zg.sanctuary.posts.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM

@Composable
fun PostActionButton(iconPainter: Painter, count: Int, onButtonClicked: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().clickable(indication = null, interactionSource = null) { onButtonClicked() }, horizontalArrangement = Arrangement.Center) {
        Image(iconPainter, contentDescription = null, modifier = Modifier.size(MARGIN_LARGE))
        Spacer(modifier = Modifier.width(MARGIN_MEDIUM))
        Text("$count", color = Color.Black)
    }
}