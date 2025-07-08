package com.zg.sanctuary.posts.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.core.HINT_COLOR
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.MARGIN_XXLARGE
import com.zg.sanctuary.core.presentation.components.SanctuaryTextField
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.image_loading_error
import sanctuary.composeapp.generated.resources.loading_skeleton

@Composable
fun WriteComment(
    loggedInUser: User,
    comment: String,
    onCommentChanged: (String) -> Unit,
    onCommentConfirmed: () -> Unit,
) {
    Row(modifier = Modifier.padding(MARGIN_MEDIUM_2), verticalAlignment = Alignment.CenterVertically) {
        // Profile Image
        AsyncImage(
            model = loggedInUser.profileImageUrl,
            placeholder = painterResource(Res.drawable.loading_skeleton),
            error = painterResource(Res.drawable.image_loading_error),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(MARGIN_XXLARGE)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(MARGIN_MEDIUM))

        // TextField
        SanctuaryTextField(
            inputText = comment,
            onInputChanged = onCommentChanged,
            hint = "Add a comment",
            fillMaxWidth = false,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(MARGIN_MEDIUM))

        Icon(
            Icons.AutoMirrored.Default.Send,
            contentDescription = null,
            tint = HINT_COLOR,
            modifier = Modifier.size(MARGIN_XLARGE).clickable {
                onCommentConfirmed()
            }
        )
    }
}