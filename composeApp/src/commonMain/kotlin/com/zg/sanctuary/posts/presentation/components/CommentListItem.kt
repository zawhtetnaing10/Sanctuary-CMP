package com.zg.sanctuary.posts.presentation.components

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
import coil3.compose.AsyncImage
import com.zg.sanctuary.comments.domain.Comment
import com.zg.sanctuary.core.HINT_COLOR
import com.zg.sanctuary.core.MARGIN_40
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.TEXT_REGULAR
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.image_loading_error
import sanctuary.composeapp.generated.resources.loading_skeleton
import sanctuary.composeapp.generated.resources.sample_profile_picture

@Composable
fun CommentListItem(comment: Comment, modifier : Modifier = Modifier){
    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier.padding(MARGIN_MEDIUM_2)
    ){
        AsyncImage(
            model = comment.user.profileImageUrl,
            placeholder = painterResource(Res.drawable.loading_skeleton),
            error = painterResource(Res.drawable.image_loading_error),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(MARGIN_40)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(MARGIN_MEDIUM_2))

        Column {
            Row{
                Text(
                    text = comment.user.fullName,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(MARGIN_MEDIUM))
                Text(
                    text = comment.formatCommentTime(),
                    color = HINT_COLOR
                )
            }

            Text(comment.content, fontSize = TEXT_REGULAR)
        }
    }
}