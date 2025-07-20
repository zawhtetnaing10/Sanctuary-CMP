package com.zg.sanctuary.conversations.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import com.zg.sanctuary.conversations.domain.ConversationResponse
import com.zg.sanctuary.core.CONVERSATION_MESSAGE_COLOR
import com.zg.sanctuary.core.MARGIN_56
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_3
import com.zg.sanctuary.core.TEXT_REGULAR_2X
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.image_loading_error
import sanctuary.composeapp.generated.resources.loading_skeleton

@Composable
fun ConversationItem(
    conversation : ConversationResponse,
    onTapConversation: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(top = MARGIN_MEDIUM_3)) {
        AsyncImage(
            model = conversation.user.profileImageUrl,
            contentDescription = null,
            placeholder = painterResource(Res.drawable.loading_skeleton),
            error = painterResource(Res.drawable.image_loading_error),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(MARGIN_56).clip(shape = CircleShape)
                .clickable {
                    onTapConversation(conversation.user.id)
                }
        )

        Spacer(modifier = Modifier.width(MARGIN_MEDIUM_2))

        Column {
            Text(conversation.user.fullName, fontSize = TEXT_REGULAR_2X, fontWeight = FontWeight.Medium)
            Text(
                conversation.lastMessage?.content ?: "",
                color = CONVERSATION_MESSAGE_COLOR,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}