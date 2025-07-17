package com.zg.sanctuary.chat.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.zg.sanctuary.core.MARGIN_56
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.MARGIN_XXLARGE
import com.zg.sanctuary.core.TEXT_REGULAR_2XX
import com.zg.sanctuary.core.TEXT_REGULAR_3X
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.sample_profile_picture

@Composable
fun ChatAppbar(
    modifier : Modifier = Modifier,
    onTapBack: () -> Unit,
){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.height(MARGIN_56)) {
        Icon(
            Icons.AutoMirrored.Default.KeyboardArrowLeft,
            contentDescription = null,
            modifier = Modifier.padding(start = MARGIN_MEDIUM).size(MARGIN_XLARGE).clickable { onTapBack() })

        Spacer(modifier = Modifier.width(MARGIN_CARD_MEDIUM_2))

        Image(
            painterResource(Res.drawable.sample_profile_picture),
            contentDescription = "User profile image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(MARGIN_XLARGE)
                .clip(CircleShape)
        )

        Text("Sofia Carter", fontSize = TEXT_REGULAR_2XX, fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = MARGIN_CARD_MEDIUM_2))
    }
}