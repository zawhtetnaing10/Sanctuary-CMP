package com.zg.sanctuary.posts.presentation.post_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_3
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.core.WRITE_COMMENT_SECTION_HEIGHT
import com.zg.sanctuary.posts.presentation.components.CommentListItem
import com.zg.sanctuary.posts.presentation.components.LikeCommentAndShareButtons
import com.zg.sanctuary.posts.presentation.components.PostContent
import com.zg.sanctuary.posts.presentation.components.PostDetailsAppbar
import com.zg.sanctuary.posts.presentation.components.SortCommentsDropDown
import com.zg.sanctuary.posts.presentation.components.WriteComment

@Composable
fun PostDetailsRoute() {
    PostDetailsScreen()
}

@Composable
fun PostDetailsScreen() {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            PostDetailsAppbar(
                onTapBack = {
                    //TODO:- Navigate back
                }
            )
        }
    ) { innerPadding ->

        Box {
            // Body
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                item {
                    // Post Data
                    Column(modifier = Modifier.padding(top = MARGIN_MEDIUM_2)) {
                        // Content
                        PostContent(onPostClicked = {})

                        Spacer(modifier = Modifier.height(MARGIN_CARD_MEDIUM_2))

                        // Like, Comment and Share
                        LikeCommentAndShareButtons(
                            onLikeClicked = {},
                            onCommentClicked = {},
                            onShareClicked = { },
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(MARGIN_MEDIUM_3))
                }

                item {
                    // Sort comment list
                    SortCommentsDropDown(modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2))
                }

                item {
                    Spacer(modifier = Modifier.height(MARGIN_MEDIUM_2))
                }

                // Comment List
                items(20) {
                    CommentListItem()
                }
            }

            // Write Comment
            Surface(
                color = Color.White,
                shadowElevation = MARGIN_SMALL,
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).height(WRITE_COMMENT_SECTION_HEIGHT)
            ) {
                WriteComment(
                    comment = "",
                    onCommentChanged = {}
                )
            }
        }
    }
}