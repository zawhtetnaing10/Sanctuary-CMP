package com.zg.sanctuary.posts.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.TEXT_REGULAR_2XX
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.camera_sanctuary
import sanctuary.composeapp.generated.resources.feed
import sanctuary.composeapp.generated.resources.friends_sanctuary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListAppbar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(stringResource(Res.string.feed), fontWeight = FontWeight.Bold, fontSize = TEXT_REGULAR_2XX)
        },
        navigationIcon = {
            Icon(
                painterResource(Res.drawable.camera_sanctuary),
                contentDescription = null,
                modifier = Modifier.padding(start = MARGIN_MEDIUM).size(MARGIN_LARGE)
            )
        },
        actions = {
            Icon(
                painterResource(Res.drawable.friends_sanctuary),
                contentDescription = null,
                modifier = Modifier.padding(end = MARGIN_MEDIUM).size(MARGIN_LARGE)
            )
        }
    )
}