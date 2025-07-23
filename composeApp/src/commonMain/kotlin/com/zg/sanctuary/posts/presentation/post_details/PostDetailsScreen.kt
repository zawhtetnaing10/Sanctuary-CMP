package com.zg.sanctuary.posts.presentation.post_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_3
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.core.WRITE_COMMENT_SECTION_HEIGHT
import com.zg.sanctuary.posts.presentation.components.CommentListItem
import com.zg.sanctuary.posts.presentation.components.PostDetailsAppbar
import com.zg.sanctuary.posts.presentation.components.SortCommentsDropDown
import com.zg.sanctuary.posts.presentation.components.WriteComment
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager
import com.zg.sanctuary.core.BOTTOM_SPACING_CHAT
import com.zg.sanctuary.posts.presentation.components.LikeCommentAndShareButtons
import com.zg.sanctuary.posts.presentation.components.PostContent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostDetailsRoute(
    viewModel: PostDetailsViewModel, onNavigateBack: () -> Unit,
    onNavigateToProfile: (Int) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    val scrollState = rememberLazyListState()


    LaunchedEffect(Unit) {
        viewModel.events.collectLatest {
            when (it) {
                PostDetailsEvent.NavigateBack -> {
                    onNavigateBack()
                }

                PostDetailsEvent.RemoveFocus -> {
                    focusManager.clearFocus()
                }

                is PostDetailsEvent.NavigateToUserProfile -> {
                    onNavigateToProfile(it.userId)
                }

                is PostDetailsEvent.ScrollToBottom -> {
                    if(it.lastIndex > 0){
                        scrollState.animateScrollToItem(index = it.lastIndex)
                    }
                }
            }
        }
    }

    PostDetailsScreen(
        state = state,
        scrollState = scrollState,
        onAction = {
            viewModel.handleAction(it)
        }
    )
}

@Composable
fun PostDetailsScreen(
    state: PostDetailsState,
    scrollState: LazyListState,
    onAction: (PostDetailsAction) -> Unit
) {
    if (state.post != null)
        Scaffold(
            containerColor = Color.White,
            topBar = {
                PostDetailsAppbar(
                    post = state.post,
                    onTapBack = {
                        onAction(PostDetailsAction.OnTapBack())
                    },
                    onTapUserProfile = {
                        onAction(PostDetailsAction.OnTapUserProfile(state.post.user.id))
                    }
                )
            }
        ) { innerPadding ->

            Box(modifier = Modifier.fillMaxSize()) {
                // Body
                LazyColumn(state = scrollState, modifier = Modifier.padding(innerPadding)) {
                    item {
                        // Post Data
                        Column(modifier = Modifier.padding(top = MARGIN_MEDIUM_2)) {
                            // Content
                            PostContent(post = state.post, onPostClicked = {})

                            Spacer(modifier = Modifier.height(MARGIN_CARD_MEDIUM_2))

                            // Like, Comment and Share
                            LikeCommentAndShareButtons(
                                post = state.post,
                                onLikeClicked = {
                                    onAction(PostDetailsAction.OnTapLike())
                                },
                                onCommentClicked = {
                                    // Do Nothing
                                },
                                onShareClicked = {
                                    // Do Nothing
                                },
                            )
                        }
                    }

//                    item {
//                        Spacer(modifier = Modifier.height(MARGIN_MEDIUM_3))
//                    }
//
//                    if (state.comments != null && state.comments.isNotEmpty())
//                        item {
//                            // Sort comment list
//                            SortCommentsDropDown(modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2))
//                        }

                    item {
                        Spacer(modifier = Modifier.height(MARGIN_MEDIUM_2))
                    }

                    // Comment List
                    if (state.comments != null && state.comments.isNotEmpty()) {
                        items(state.comments.count()) { index ->
                            CommentListItem(comment = state.comments[index])
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(BOTTOM_SPACING_CHAT))
                    }
                }

                // Write Comment
                if (state.loggedInUser != null)
                    Surface(
                        color = Color.White,
                        shadowElevation = MARGIN_SMALL,
                        modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).height(WRITE_COMMENT_SECTION_HEIGHT)
                    ) {
                        WriteComment(
                            loggedInUser = state.loggedInUser,
                            comment = state.currentComment,
                            onCommentChanged = {
                                onAction(PostDetailsAction.OnCommentChanged(it))
                            },
                            onCommentConfirmed = {
                                onAction(PostDetailsAction.OnTapPostComment())
                            }
                        )
                    }
            }
        }
}