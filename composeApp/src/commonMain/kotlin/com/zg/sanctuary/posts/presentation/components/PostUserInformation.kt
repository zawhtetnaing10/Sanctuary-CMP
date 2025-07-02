package com.zg.sanctuary.posts.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.core.HINT_COLOR
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_3
import com.zg.sanctuary.core.MARGIN_XXLARGE
import com.zg.sanctuary.core.TEXT_REGULAR
import com.zg.sanctuary.core.TEXT_SMALL
import com.zg.sanctuary.posts.domain.Post
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.image_loading_error
import sanctuary.composeapp.generated.resources.loading_skeleton
import sanctuary.composeapp.generated.resources.sample_profile_picture

@Composable
fun PostUserInformation(post: Post, modifier : Modifier = Modifier){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = post.user.profileImageUrl,
            contentDescription = "User profile image",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(Res.drawable.loading_skeleton),
            error = painterResource(Res.drawable.image_loading_error),
            modifier = Modifier
                .size(MARGIN_XXLARGE)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(MARGIN_MEDIUM))

        Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
            // Name
            Text(
                text = post.user.fullName,
                fontSize = TEXT_REGULAR,
                fontWeight = FontWeight.Bold,
            )
            // Date
            Text(
                post.formatPostTime(),
                color = HINT_COLOR,
                fontSize = TEXT_SMALL
            )
        }
    }
}