package com.zg.sanctuary.posts.presentation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zg.sanctuary.core.DIVIDER_COLOR
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.posts.presentation.components.CreatePostView
import com.zg.sanctuary.posts.presentation.components.PostListAppBar
import com.zg.sanctuary.posts.presentation.components.PostListItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PostListRoute() {
    PostListScreen()
}

@Composable
fun PostListScreen() {
    Scaffold(
        containerColor = Color.White,
    ) { innerPadding ->
        // Add Body here.
        LazyColumn(modifier = Modifier.padding(innerPadding)) {

            item{
                PostListAppBar()
            }

            // Create post view
            item {
                CreatePostView(
                    onCreateConfirmed = {
                        // TODO: - Navigate to create post screen
                    }
                )
            }

            // Horizontal Divider.
            item{
                HorizontalDivider(color = DIVIDER_COLOR, modifier = Modifier.height(MARGIN_SMALL))
            }

            // Post List
            items (20) {
                PostListItem(onLikeClicked = {}, onCommentClicked = {}, onShareClicked = {})

                // Divider
                if(it < 19){
                    HorizontalDivider(color = DIVIDER_COLOR, modifier = Modifier.height(MARGIN_SMALL))
                }
            }
        }
    }
}

@Preview
@Composable
fun PostListScreenPreview() {
    PostListScreen()
}
