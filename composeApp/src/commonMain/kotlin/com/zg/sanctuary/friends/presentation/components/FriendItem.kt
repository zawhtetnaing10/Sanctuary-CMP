package com.zg.sanctuary.friends.presentation.components

import androidx.compose.foundation.Image
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
import com.zg.sanctuary.core.MARGIN_40
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.TEXT_REGULAR_2X
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.sample_profile_picture

@Composable
fun FriendItem(
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(top = MARGIN_MEDIUM_2)) {
        // Profile Image
        Image(
            painterResource(Res.drawable.sample_profile_picture),
            contentDescription = null,
            modifier = Modifier.size(MARGIN_40).clip(shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(MARGIN_MEDIUM_2))

        // Name and confirm button
        Text("Ethan Carter", fontSize = TEXT_REGULAR_2X)
    }
}