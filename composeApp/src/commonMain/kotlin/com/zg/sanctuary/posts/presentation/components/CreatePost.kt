package com.zg.sanctuary.posts.presentation.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.core.HINT_COLOR
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_XXLARGE
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.create_post_label
import sanctuary.composeapp.generated.resources.image_loading_error
import sanctuary.composeapp.generated.resources.loading_skeleton

@Composable
fun CreatePostView(user: User, onCreateConfirmed: () -> Unit, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(MARGIN_MEDIUM_2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.profileImageUrl,
            contentDescription = "User profile image",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(Res.drawable.loading_skeleton),
            error = painterResource(Res.drawable.image_loading_error),
            modifier = Modifier
                .size(MARGIN_XXLARGE)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(MARGIN_MEDIUM_2))

        Text(stringResource(Res.string.create_post_label), color = HINT_COLOR, modifier = Modifier.clickable { onCreateConfirmed() })
    }
}