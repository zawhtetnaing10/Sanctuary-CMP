package com.zg.sanctuary.posts.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.TEXT_REGULAR_4X
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.app_name
import sanctuary.composeapp.generated.resources.sanctuary_chat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListAppBar(
    onTapChat: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                stringResource(Res.string.app_name),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = TEXT_REGULAR_4X
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        actions = {
            Icon(
                painterResource(Res.drawable.sanctuary_chat),
                contentDescription = null,
                modifier = Modifier.size(MARGIN_XLARGE).padding(end = MARGIN_MEDIUM).clickable {
                    onTapChat()
                })
        }
    )
}