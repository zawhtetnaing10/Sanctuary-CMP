package com.zg.sanctuary.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.TEXT_REGULAR_4X

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralAppbar(
    title: String,
    onTapBack: () -> Unit
){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        title = {
            Text(
                title,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = TEXT_REGULAR_4X
            )
        },
        navigationIcon = {
            Icon(
                Icons.AutoMirrored.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier.size(MARGIN_XLARGE)
                    .clickable {
                        onTapBack()
                    }
            )
        }
    )
}