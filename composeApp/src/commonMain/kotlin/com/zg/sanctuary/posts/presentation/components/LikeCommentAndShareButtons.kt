package com.zg.sanctuary.posts.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_3
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.sanctuary_comment
import sanctuary.composeapp.generated.resources.sanctuary_like
import sanctuary.composeapp.generated.resources.sanctuary_share

@Composable
fun LikeCommentAndShareButtons(
    onLikeClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    onShareClicked: () -> Unit,
    modifier : Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(top = MARGIN_MEDIUM_2, bottom = MARGIN_MEDIUM_3),
    ) {
        // Like button
        PostActionButton(
            iconPainter = painterResource(Res.drawable.sanctuary_like),
            count = 23,
            onButtonClicked = {
                onLikeClicked()
            },
            modifier = Modifier.weight(1.0f, fill = true)
        )

        // Comment button
        PostActionButton(
            iconPainter = painterResource(Res.drawable.sanctuary_comment),
            count = 30,
            onButtonClicked = {
                onCommentClicked()
            },
            modifier = Modifier.weight(1.0f, fill = true)
        )

        // Post Action
        PostActionButton(
            iconPainter = painterResource(Res.drawable.sanctuary_share),
            count = 5,
            onButtonClicked = {
                onShareClicked()
            },
            modifier = Modifier.weight(1.0f, fill = true)
        )
    }
}
