package com.zg.sanctuary.posts.presentation.create_post

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zg.sanctuary.core.HINT_COLOR
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.MARGIN_XXLARGE
import com.zg.sanctuary.core.TEXT_REGULAR_2X
import com.zg.sanctuary.posts.presentation.components.CreatePostAppbar
import com.zg.sanctuary.posts.presentation.components.CreatePostInput
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.create_post_label
import sanctuary.composeapp.generated.resources.image_sanctuary
import sanctuary.composeapp.generated.resources.sample_profile_picture

@Composable
fun CreatePostRoute() {
    CreatePostScreen()
}

@Composable
fun CreatePostScreen() {
    Scaffold(
        topBar = {
            CreatePostAppbar(
                onTapBack = {},
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {

            // Logged in user
            item {
                CreatePostLoggedInUser()
            }

            item {
                Spacer(modifier = Modifier.height(MARGIN_MEDIUM))
            }

            item {
                // Input box
                CreatePostInput(
                    onImagePicked = {},
                    onDeleteImage = {}
                )
            }

            item{
                Spacer(modifier = Modifier.height(MARGIN_XXLARGE))
            }
        }
    }
}

@Composable
fun CreatePostLoggedInUser(){
    Row(
        modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_MEDIUM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.sample_profile_picture),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(MARGIN_XXLARGE)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(MARGIN_MEDIUM))
        Text(
            text = "Chloe Bennett",
            fontSize = TEXT_REGULAR_2X,
            fontWeight = FontWeight.Bold,
        )
    }
}