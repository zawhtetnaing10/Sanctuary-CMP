package com.zg.sanctuary.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonAppbar(
    title: String,
    onTapBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(title, fontWeight = FontWeight.Bold, fontSize = TEXT_REGULAR_2XX)
        },
        navigationIcon = {
            Icon(
                Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = MARGIN_MEDIUM)
                    .size(MARGIN_LARGE)
                    .clickable {
                        onTapBack()
                    }
            )
        },
    )
}