package com.zg.sanctuary.chat.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.chat.domain.ChatMessage
import com.zg.sanctuary.core.MARGIN_40
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_3
import com.zg.sanctuary.core.MESSAGE_BUBBLE_COLOR
import com.zg.sanctuary.core.MESSAGE_BUBBLE_COLOR_RECEIVER
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.image_loading_error
import sanctuary.composeapp.generated.resources.loading_skeleton

@Composable
fun ChatItem(
    loggedInUser: User,
    otherUser: User,
    chatMessage: ChatMessage,
    modifier: Modifier = Modifier
) {

    val isSenderLoggedInUser = chatMessage.senderId == loggedInUser.id
    val backgroundColor = if (isSenderLoggedInUser) MESSAGE_BUBBLE_COLOR else MESSAGE_BUBBLE_COLOR_RECEIVER
    val textColor = if (isSenderLoggedInUser) Color.White else Color.Black

    val horizontalArrangement = if (isSenderLoggedInUser) Arrangement.End else Arrangement.Start

    Row(modifier = Modifier.padding(top = MARGIN_MEDIUM_3).fillMaxWidth(), horizontalArrangement = horizontalArrangement) {
        if (!isSenderLoggedInUser) {
            AsyncImage(
                model = otherUser.profileImageUrl,
                contentDescription = null,
                placeholder = painterResource(Res.drawable.loading_skeleton),
                error = painterResource(Res.drawable.image_loading_error),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(MARGIN_40).clip(shape = CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(MARGIN_MEDIUM_2))

        // Left margin for messages sent by the user.
        if(isSenderLoggedInUser){
            Spacer(modifier = Modifier.width(MARGIN_40))
        }

        Surface(
            shape = RoundedCornerShape(MARGIN_MEDIUM), color = backgroundColor,
        ) {
            Text(
                chatMessage.content, color = textColor,
                modifier = Modifier.padding(MARGIN_MEDIUM),
            )
        }
    }
}