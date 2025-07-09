package com.zg.sanctuary.profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.zg.sanctuary.core.DIVIDER_COLOR
import com.zg.sanctuary.core.HINT_COLOR
import com.zg.sanctuary.core.MARGIN_56
import com.zg.sanctuary.core.MARGIN_CARD_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.PROFILE_PICTURE_SIZE
import com.zg.sanctuary.core.TEXT_REGULAR_2X
import com.zg.sanctuary.core.TEXT_REGULAR_3X
import com.zg.sanctuary.core.TEXT_REGULAR_4X
import com.zg.sanctuary.core.presentation.components.InterestsSelection
import com.zg.sanctuary.core.presentation.components.SanctuaryPrimaryButton
import com.zg.sanctuary.core.presentation.components.dialogs.ErrorDialog
import com.zg.sanctuary.core.presentation.components.dialogs.LoadingDialog
import com.zg.sanctuary.posts.presentation.components.PostListItem
import com.zg.sanctuary.profile.presentation.components.ProfileAppbar
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.friend_request_pending
import sanctuary.composeapp.generated.resources.image_loading_error
import sanctuary.composeapp.generated.resources.loading_skeleton
import sanctuary.composeapp.generated.resources.log_out
import sanctuary.composeapp.generated.resources.posts
import sanctuary.composeapp.generated.resources.profile_friends
import sanctuary.composeapp.generated.resources.send_friend_request
import sanctuary.composeapp.generated.resources.sent_you_a_friend_request


@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToPostDetails: (Int) -> Unit,
    onNavigateBackToLogin: () -> Unit
) {

    // State
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest {
            when (it) {
                ProfileEvents.NavigateBack -> {
                    onNavigateBack()
                }

                ProfileEvents.NavigateBackToLogin -> {
                    onNavigateBackToLogin()
                }

                is ProfileEvents.NavigateToPostDetails -> {
                    onNavigateToPostDetails(it.postId)
                }
            }
        }
    }

    ProfileScreen(
        state = state,
        onAction = {
            viewModel.handleAction(it)
        }
    )
}

@Composable
fun ProfileScreen(
    state: ProfileState,
    onAction: (ProfileActions) -> Unit
) {

    if (state.isLoading) {
        LoadingDialog(onDismissRequest = {})
    }

    if (state.error.isNotEmpty()) {
        ErrorDialog(
            onDismissRequest = {
                onAction(ProfileActions.OnErrorDialogDismissed())
            },
            message = state.error
        )
    }

    Scaffold(
        topBar = {
            ProfileAppbar(
                onTapBack = {
                    onAction(ProfileActions.OnTapBack)
                }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            // Profile Info
            state.userProfileDetails?.user?.let { user ->
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = MARGIN_CARD_MEDIUM_2).padding(horizontal = MARGIN_MEDIUM_2)
                    ) {
                        // Profile Image
                        AsyncImage(
                            model = user.profileImageUrl,
                            placeholder = painterResource(Res.drawable.loading_skeleton),
                            error = painterResource(Res.drawable.image_loading_error),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(PROFILE_PICTURE_SIZE)
                                .clip(shape = CircleShape)
                        )
                        Spacer(Modifier.width(MARGIN_MEDIUM_2))
                        // Profile Info
                        Column(verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM)) {
                            Text(user.fullName, fontWeight = FontWeight.Bold, fontSize = TEXT_REGULAR_3X)
                            Text(user.email, fontSize = TEXT_REGULAR_2X, color = HINT_COLOR)
                            Text("Joined in ${user.getJoinedDate()}", fontSize = TEXT_REGULAR_2X, color = HINT_COLOR)
                        }
                    }
                }
            }


            // Friends or not button
            if (state.userStatus == UserStatus.FRIENDS)
                item {
                    SanctuaryPrimaryButton(
                        title = stringResource(Res.string.profile_friends),
                        onClick = {},
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = MARGIN_MEDIUM_2)
                            .padding(horizontal = MARGIN_MEDIUM_2)
                    )
                }

            // Sent you a friend request button
            if (state.userStatus == UserStatus.RECEIVED_FRIEND_REQUEST)
                item {
                    SanctuaryPrimaryButton(
                        title = stringResource(Res.string.sent_you_a_friend_request), onClick = {
                            onAction(ProfileActions.OnAcceptFriendRequest)
                        },
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = MARGIN_MEDIUM_2)
                            .padding(horizontal = MARGIN_MEDIUM_2)
                    )
                }

            // Friend request pending button
            if (state.userStatus == UserStatus.FRIEND_REQUEST_PENDING)
                item {
                    SanctuaryPrimaryButton(
                        title = stringResource(Res.string.friend_request_pending),
                        onClick = {},
                        enabled = false,
                        modifier = Modifier.fillMaxWidth().padding(top = MARGIN_MEDIUM_2).padding(horizontal = MARGIN_MEDIUM_2)
                    )
                }

            // Send Friend Request
            if (state.userStatus == UserStatus.ABLE_TO_SEND_FRIEND_REQUEST)
                item {
                    SanctuaryPrimaryButton(
                        title = stringResource(Res.string.send_friend_request),
                        onClick = {
                            onAction(ProfileActions.OnTapSendFriendRequest)
                        },
                        modifier = Modifier.fillMaxWidth().padding(top = MARGIN_MEDIUM_2).padding(horizontal = MARGIN_MEDIUM_2)
                    )
                }

            // Logout Button
            if (state.userStatus == UserStatus.LOGGED_IN_USER)
                item {
                    SanctuaryPrimaryButton(
                        title = stringResource(Res.string.log_out), onClick = {
                            onAction(ProfileActions.OnTapLogout)
                        }, modifier = Modifier.fillMaxWidth().padding(top = MARGIN_MEDIUM_2).padding(horizontal = MARGIN_MEDIUM_2)
                    )
                }

            // Interests
            state.userProfileDetails?.user?.interests?.let {
                if (it.isNotEmpty())
                    item {
                        InterestsSelection(
                            interests = state.userProfileDetails.user.interests,
                            allowSelection = false,
                            chosenInterests = setOf(),
                            onInterestPicked = {
                                // Do nothing
                            },
                            modifier = Modifier.padding(top = MARGIN_LARGE).padding(horizontal = MARGIN_MEDIUM_2)
                        )
                    }
            }


            item {
                Spacer(Modifier.height(MARGIN_XLARGE))
            }

            // Posts by user.
            if(state.postsByUser.isNotEmpty()){

                // Posts Title
                item {
                    Text(
                        stringResource(Res.string.posts),
                        fontSize = TEXT_REGULAR_4X,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2)
                    )
                }

                item {
                    Spacer(Modifier.height(MARGIN_MEDIUM_2))
                }

                // Posts by user.
                items(state.postsByUser.count()) { index ->
                    val post = state.postsByUser[index]
                    PostListItem(
                        post = post,
                        onLikeClicked = {
                            onAction(ProfileActions.OnTapLike(post.id))
                        }, onCommentClicked = {
                            onAction(ProfileActions.OnTapComment(post.id))
                        }, onShareClicked = {
                            // Do Nothing
                        }, onPostClicked = {
                            onAction(ProfileActions.OnTapPost(post.id))
                        }, onUserClicked = {
                            // Do Nothing
                        })

                    // Divider
                    if (index < state.postsByUser.count() - 1) {
                        HorizontalDivider(color = DIVIDER_COLOR, modifier = Modifier.height(MARGIN_SMALL))
                    }
                }
            }



            // Bottom Margin
            item {
                Spacer(modifier = Modifier.height(MARGIN_56))
            }
        }
    }
}