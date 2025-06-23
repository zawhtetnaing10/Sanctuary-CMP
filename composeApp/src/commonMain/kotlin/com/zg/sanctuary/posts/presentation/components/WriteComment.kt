package com.zg.sanctuary.posts.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_XXLARGE
import com.zg.sanctuary.core.presentation.components.SanctuaryTextField
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.sample_profile_picture

@Composable
fun WriteComment(comment: String, onCommentChanged: (String) -> Unit) {
    Row(modifier = Modifier.padding(MARGIN_MEDIUM_2), verticalAlignment = Alignment.CenterVertically) {
        // Profile Image
        Image(
            painter = painterResource(Res.drawable.sample_profile_picture),
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
        )
    }
}