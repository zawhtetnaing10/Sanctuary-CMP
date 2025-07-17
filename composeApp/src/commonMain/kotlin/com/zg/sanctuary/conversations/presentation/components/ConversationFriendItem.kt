package com.zg.sanctuary.conversations.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import com.zg.sanctuary.core.MARGIN_56
import com.zg.sanctuary.core.MARGIN_64
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.TEXT_REGULAR
import com.zg.sanctuary.core.TEXT_REGULAR_2X
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.sample_profile_picture

@Composable
fun ConversationFriendItem(
    onTapFriend : (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(end = MARGIN_MEDIUM_2)) {
        // Profile Image
//        AsyncImage(
//            model = friend.user?.profileImageUrl,
//            contentDescription = null,
//            placeholder = painterResource(Res.drawable.loading_skeleton),
//            error = painterResource(Res.drawable.image_loading_error),
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.size(MARGIN_40).clip(shape = CircleShape)
//                .clickable {
//                    friend.user?.let { user ->
//                        onTapProfile(user.id)
//                    }
//                }
//        )

        // Profile Image // TODO:- Replace with async image later
        Image(
            painterResource(Res.drawable.sample_profile_picture),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(MARGIN_64).clip(shape = CircleShape)
                .clickable {
                   onTapFriend(0)
                }
        )

        Spacer(modifier = Modifier.width(MARGIN_MEDIUM_2))

        // Name and confirm button
        Text("Sophia", fontSize = TEXT_REGULAR)
    }
}