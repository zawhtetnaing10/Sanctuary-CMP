package com.zg.sanctuary.posts.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.TEXT_REGULAR_3X
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.new_post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostAppbar(
    onTapBack: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(Res.string.new_post),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = TEXT_REGULAR_3X
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        navigationIcon = {
            Icon(
                Icons.AutoMirrored.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier.padding(start = MARGIN_MEDIUM).size(MARGIN_XLARGE).clickable { onTapBack() })
        },
    )
}