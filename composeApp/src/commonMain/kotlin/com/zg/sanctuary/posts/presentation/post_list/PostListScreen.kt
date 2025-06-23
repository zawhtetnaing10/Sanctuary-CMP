package com.zg.sanctuary.posts.presentation.post_list

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zg.sanctuary.core.DIVIDER_COLOR
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.posts.presentation.components.CreatePostView
import com.zg.sanctuary.posts.presentation.components.PostListAppBar
import com.zg.sanctuary.posts.presentation.components.PostListItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostListRoute(
    viewModel: PostListViewModel,
    onNavigateToCreatePost: () -> Unit,
    onNavigateToPostDetails: (Int) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest {
            when (it) {
                is PostListEvent.NavigateToCreatePost -> {
                    onNavigateToCreatePost()
                }

                is PostListEvent.NavigateToPostDetails -> {
                    onNavigateToPostDetails(it.id)
                }
            }
        }
    }

    PostListScreen(state, onAction = {
        viewModel.handleAction(it)
    })
}

@Composable
fun PostListScreen(state: PostListState, onAction: (PostListAction) -> Unit) {
    Scaffold(
        containerColor = Color.White,
    ) { innerPadding ->
        // Add Body here.
        LazyColumn(modifier = Modifier.padding(innerPadding)) {

            // Appbar
            item {
                PostListAppBar(
                    onTapSearch = {
                        onAction(PostListAction.OnTapSearch())
                    }
                )
            }

            // Create post view
            item {
                CreatePostView(
                    onCreateConfirmed = {
                        onAction(PostListAction.OnTapCreatePost())
                    }
                )
            }

            // Horizontal Divider.
            item {
                HorizontalDivider(color = DIVIDER_COLOR, modifier = Modifier.height(MARGIN_SMALL))
            }

            // Post List
            items(20) {
                PostListItem(onLikeClicked = {
                    onAction(PostListAction.OnTapLike(0))
                }, onCommentClicked = {
                    onAction(PostListAction.OnTapComment(0))
                }, onShareClicked = {
                    onAction(PostListAction.OnTapShare(0))
                }, onPostClicked = {
                    onAction(PostListAction.OnTapPost(0))
                })

                // Divider
                if (it < 19) {
                    HorizontalDivider(color = DIVIDER_COLOR, modifier = Modifier.height(MARGIN_SMALL))
                }
            }
        }
    }
}
