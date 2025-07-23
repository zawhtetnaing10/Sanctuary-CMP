package com.zg.sanctuary.posts.presentation.components

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
import coil3.compose.AsyncImage
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.POST_IMAGE_HEIGHT
import com.zg.sanctuary.core.TEXT_REGULAR
import com.zg.sanctuary.posts.domain.Post
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.image_loading_error
import sanctuary.composeapp.generated.resources.loading_skeleton

@Composable
fun PostContent(post: Post, onPostClicked: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            post.content,
            fontSize = TEXT_REGULAR,
            modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2)
                .clickable(indication = null, interactionSource = null, onClick = { onPostClicked() })
        )

        Spacer(modifier = Modifier.height(MARGIN_CARD_MEDIUM_2))

        // Post Image
        if(post.mediaUrl.isNotEmpty()){
            AsyncImage(
                model = post.mediaUrl,
                contentDescription = "Post image",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(Res.drawable.loading_skeleton),
                error = painterResource(Res.drawable.image_loading_error),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(POST_IMAGE_HEIGHT)
            )
        }
    }
}