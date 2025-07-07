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
import com.zg.sanctuary.posts.domain.Post
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
    post : Post,
    onLikeClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onPostClicked: () -> Unit,
    onUserClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        // Profile Image and Name
        PostUserInformation(
            post = post,modifier = Modifier
                .padding(top = MARGIN_MEDIUM_3, start = MARGIN_MEDIUM_2, end = MARGIN_MEDIUM_2),
            onUserClicked = onUserClicked
        )

        Spacer(modifier = Modifier.height(MARGIN_CARD_MEDIUM_2))

        // Content
        PostContent(post = post, onPostClicked = onPostClicked)

        Spacer(modifier = Modifier.height(MARGIN_CARD_MEDIUM_2))

        // Like, Comment and Share
        LikeCommentAndShareButtons(
            post = post,
            onLikeClicked = onLikeClicked,
            onCommentClicked = onCommentClicked,
            onShareClicked = onShareClicked,
        )
    }
}