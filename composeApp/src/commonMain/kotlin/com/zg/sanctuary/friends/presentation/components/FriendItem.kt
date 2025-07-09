package com.zg.sanctuary.friends.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import coil3.compose.AsyncImage
import com.zg.sanctuary.core.MARGIN_40
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.TEXT_REGULAR_2X
import com.zg.sanctuary.friends.domain.FriendRequest
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.image_loading_error
import sanctuary.composeapp.generated.resources.loading_skeleton

@Composable
fun FriendItem(
    friend: FriendRequest,
    onTapProfile: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(top = MARGIN_MEDIUM_2)) {
        // Profile Image
        AsyncImage(
            model = friend.user?.profileImageUrl,
            contentDescription = null,
            placeholder = painterResource(Res.drawable.loading_skeleton),
            error = painterResource(Res.drawable.image_loading_error),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(MARGIN_40).clip(shape = CircleShape)
                .clickable {
                    friend.user?.let { user ->
                        onTapProfile(user.id)
                    }
                }
        )

        Spacer(modifier = Modifier.width(MARGIN_MEDIUM_2))

        // Name and confirm button
        Text(friend.user?.fullName ?: "", fontSize = TEXT_REGULAR_2X)
    }
}