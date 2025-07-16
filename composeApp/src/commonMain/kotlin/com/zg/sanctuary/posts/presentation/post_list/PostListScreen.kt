package com.zg.sanctuary.posts.presentation.post_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zg.sanctuary.core.BOTTOM_SPACING
import com.zg.sanctuary.core.DIVIDER_COLOR
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.core.presentation.components.dialogs.ErrorDialog
import com.zg.sanctuary.core.presentation.components.dialogs.LoadingDialog
import com.zg.sanctuary.posts.presentation.components.CreatePostView
import com.zg.sanctuary.posts.presentation.components.PostListAppBar
import com.zg.sanctuary.posts.presentation.components.PostListItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostListRoute(
    viewModel: PostListViewModel,
    onNavigateToCreatePost: () -> Unit,
    onNavigateToPostDetails: (Int) -> Unit,
    onNavigateToProfile: (Int) -> Unit,
    onNavigateToChat : () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    // Set up lazy list state
    val lazyListState = rememberLazyListState()

    // Set up callback for load more.
    val shouldLoadMore = remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            val totalItemsCount = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            lastVisibleItemIndex >= totalItemsCount - 1
        }
    }

    DisposableEffect(Unit) {

        // navigated to or navigated back
        viewModel.handleAction(PostListAction.OnPostListScreenReached())

        onDispose {
            // Clean up
        }
    }

    // Launched Effect for load more
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            viewModel.handleAction(PostListAction.OnListEndReached())
        }
    }

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest {
            when (it) {
                is PostListEvent.NavigateToCreatePost -> {
                    onNavigateToCreatePost()
                }

                is PostListEvent.NavigateToPostDetails -> {
                    onNavigateToPostDetails(it.id)
                }

                is PostListEvent.NavigateToUserProfile -> {
                    onNavigateToProfile(it.userId)
                }

                is PostListEvent.NavigateToChat -> {
                    onNavigateToChat()
                }
            }
        }
    }

    PostListScreen(state, lazyListState, onAction = {
        viewModel.handleAction(it)
    })
}

@Composable
fun PostListScreen(state: PostListState, lazyListState: LazyListState, onAction: (PostListAction) -> Unit) {

    if (state.isLoading) {
        LoadingDialog(onDismissRequest = {})
    }

    if (state.error.isNotEmpty()) {
        ErrorDialog(
            onDismissRequest = {
                onAction(PostListAction.OnErrorDialogDismissed())
            },
            message = state.error
        )
    }

    Scaffold(
        containerColor = Color.White,
    ) { innerPadding ->
        // Add Body here.
        LazyColumn(state = lazyListState, modifier = Modifier.padding(innerPadding).padding(bottom = BOTTOM_SPACING)) {

            // Appbar
            item {
                PostListAppBar(
                    onTapChat = {
                        onAction(PostListAction.OnTapChat())
                    }
                )
            }

            // Create post view
            if(state.loggedInUser != null)
                item {
                    CreatePostView(
                        user = state.loggedInUser,
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
            items(state.posts.count()) { index ->
                val post = state.posts[index]
                PostListItem(
                    post = post,
                    onLikeClicked = {
                        onAction(PostListAction.OnTapLike(post.id))
                    }, onCommentClicked = {
                        onAction(PostListAction.OnTapComment(post.id))
                    }, onShareClicked = {
                        onAction(PostListAction.OnTapShare(post.id))
                    }, onPostClicked = {
                        onAction(PostListAction.OnTapPost(post.id))
                    }, onUserClicked = {
                        onAction(PostListAction.OnTapProfile(post.user.id))
                    })

                // Divider
                if (index < state.posts.count() - 1) {
                    HorizontalDivider(color = DIVIDER_COLOR, modifier = Modifier.height(MARGIN_SMALL))
                }
            }


            // Loading more view
            if (state.isLoadingMore) {
                item {
                    Box(modifier = Modifier.background(color = Color.White).fillMaxWidth()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}
