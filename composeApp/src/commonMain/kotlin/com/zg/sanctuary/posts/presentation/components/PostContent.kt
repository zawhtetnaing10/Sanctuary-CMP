package com.zg.sanctuary.posts.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.POST_IMAGE_HEIGHT
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.placeholder_post_image

@Composable
fun PostContent(onPostClicked: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            "I'm so excited to share my latest travel adventure with you all! I've been exploring the hidden gems of the Italian countryside, and the scenery is absolutely breathtaking. From rolling hills to charming villages, every corner is a postcard-worthy moment. Stay tuned for more updates and photos!",
            modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2)
                .clickable(indication = null, interactionSource = null, onClick = { onPostClicked() })
        )

        Spacer(modifier = Modifier.height(MARGIN_CARD_MEDIUM_2))

        // Post Image
        Image(
            painter = painterResource(Res.drawable.placeholder_post_image),
            contentDescription = null,
            contentScale = ContentScale.Crop, // Crop to fill bounds
            modifier = Modifier
                .fillMaxWidth()
                .height(POST_IMAGE_HEIGHT)
        )
    }
}