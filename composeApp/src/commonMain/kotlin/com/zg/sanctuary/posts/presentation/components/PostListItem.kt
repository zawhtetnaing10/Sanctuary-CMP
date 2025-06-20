package com.zg.sanctuary.posts.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zg.sanctuary.core.HINT_COLOR
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_3
import com.zg.sanctuary.core.MARGIN_XXLARGE
import com.zg.sanctuary.core.POST_IMAGE_HEIGHT
import com.zg.sanctuary.core.TEXT_SMALL
import com.zg.sanctuary.core.TEXT_SMALL_2X
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.placeholder_post_image
import sanctuary.composeapp.generated.resources.sample_profile_picture
import sanctuary.composeapp.generated.resources.sanctuary_comment
import sanctuary.composeapp.generated.resources.sanctuary_like
import sanctuary.composeapp.generated.resources.sanctuary_share

@Composable
fun PostListItem(
    onLikeClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onPostClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        // Profile Image and Name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(top = MARGIN_MEDIUM_3, start = MARGIN_MEDIUM_2, end = MARGIN_MEDIUM_2)
        ) {
            Image(
                painter = painterResource(Res.drawable.sample_profile_picture),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(MARGIN_XXLARGE)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(MARGIN_MEDIUM))

            Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                // Name
                Text(
                    text = "Chloe Bennett",
                    fontWeight = FontWeight.Bold,
                )
                // Date
                Text(
                    "1 hour ago",
                    color = HINT_COLOR,
                    fontSize = TEXT_SMALL
                )
            }
        }

        Spacer(modifier = Modifier.height(MARGIN_CARD_MEDIUM_2))

        // Content
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

        Spacer(modifier = Modifier.height(12.dp))

        // Like, Comment and Share
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = MARGIN_MEDIUM_2, bottom = MARGIN_MEDIUM_3),
        ) {
            // Like button
            PostActionButton(
                iconPainter = painterResource(Res.drawable.sanctuary_like),
                count = 23,
                onButtonClicked = {
                    onLikeClicked()
                },
                modifier = Modifier.weight(1.0f, fill = true)
            )

            // Comment button
            PostActionButton(
                iconPainter = painterResource(Res.drawable.sanctuary_comment),
                count = 30,
                onButtonClicked = {
                    onCommentClicked()
                },
                modifier = Modifier.weight(1.0f, fill = true)
            )

            // Post Action
            PostActionButton(
                iconPainter = painterResource(Res.drawable.sanctuary_share),
                count = 5,
                onButtonClicked = {
                    onShareClicked()
                },
                modifier = Modifier.weight(1.0f, fill = true)
            )
        }
    }
}

@Composable
fun PostActionButton(iconPainter: Painter, count: Int, onButtonClicked: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().clickable(indication = null, interactionSource = null) { onButtonClicked() }, horizontalArrangement = Arrangement.Center) {
        Icon(iconPainter, contentDescription = null, modifier = Modifier.size(MARGIN_LARGE))
        Spacer(modifier = Modifier.width(MARGIN_MEDIUM))
        Text("$count", color = Color.Black)
    }
}