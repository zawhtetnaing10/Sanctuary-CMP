package com.zg.sanctuary.posts.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zg.sanctuary.core.MARGIN_56
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_XLARGE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailsAppbar(
    onTapBack: () -> Unit,
    modifier : Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.height(MARGIN_56)) {
        Icon(
            Icons.AutoMirrored.Default.KeyboardArrowLeft,
            contentDescription = null,
            modifier = Modifier.padding(start = MARGIN_MEDIUM).size(MARGIN_XLARGE).clickable { onTapBack() })

        Spacer(modifier = Modifier.width(MARGIN_CARD_MEDIUM_2))

        // TODO: - Uncomment after finishing post list
        //PostUserInformation()
    }
}