package com.zg.sanctuary.chat.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.zg.sanctuary.core.MARGIN_40
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_3
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.core.MESSAGE_BUBBLE_COLOR
import com.zg.sanctuary.core.MESSAGE_BUBBLE_COLOR_RECEIVER
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.sample_profile_picture

@Composable
fun ChatItem(
    isSenderLoggedInUser : Boolean,
    modifier : Modifier = Modifier
){

    val backgroundColor = if(isSenderLoggedInUser) MESSAGE_BUBBLE_COLOR else MESSAGE_BUBBLE_COLOR_RECEIVER
    val textColor = if(isSenderLoggedInUser) Color.White else Color.Black

    Row(modifier = Modifier.padding(top = MARGIN_MEDIUM_3)) {
        // TODO: - replace with async image later
        if(!isSenderLoggedInUser)
            Image(
                painterResource(Res.drawable.sample_profile_picture),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(MARGIN_40).clip(shape = CircleShape)
            )

        Spacer(modifier = Modifier.width(MARGIN_MEDIUM_2))

        Surface(shape = RoundedCornerShape(MARGIN_MEDIUM), color = backgroundColor, modifier= Modifier.weight(1.0f)) {
            Text("Hey what are you doing?", color = textColor, modifier = Modifier.padding(MARGIN_MEDIUM))
        }

        Spacer(modifier = Modifier.width(MARGIN_MEDIUM_2))

        if(isSenderLoggedInUser)
            Image(
                painterResource(Res.drawable.sample_profile_picture),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(MARGIN_40).clip(shape = CircleShape)
            )
    }
}