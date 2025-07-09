package com.zg.sanctuary.friends.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zg.sanctuary.core.BOTTOM_SPACING
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.TEXT_REGULAR_3XX
import com.zg.sanctuary.friends.presentation.components.FriendItem
import com.zg.sanctuary.friends.presentation.components.FriendRequestItem
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.friend_requests
import sanctuary.composeapp.generated.resources.friends
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.zg.sanctuary.core.presentation.components.dialogs.ErrorDialog
import com.zg.sanctuary.core.presentation.components.dialogs.LoadingDialog
import kotlinx.coroutines.flow.collectLatest
import sanctuary.composeapp.generated.resources.friends_empty

@Composable
fun FriendsRoute(
    viewModel: FriendsViewModel,
    onNavigateToProfile: (Int) -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest {
            when (it) {
                is FriendsEvent.NavigateToUserProfile -> {
                    onNavigateToProfile(it.userId)
                }
            }
        }
    }

    FriendsScreen(
        state = state,
        onAction = {
            viewModel.handleAction(it)
        }
    )
}

@Composable
fun FriendsScreen(
    state: FriendsState,
    onAction: (FriendsAction) -> Unit
) {

    if (state.isLoading) {
        LoadingDialog(onDismissRequest = {})
    }

    if (state.error.isNotEmpty()) {
        ErrorDialog(
            onDismissRequest = {
                onAction(FriendsAction.OnErrorDialogDismissed)
            },
            message = state.error
        )
    }

    Scaffold(containerColor = Color.White) { innerPadding ->

        if (state.friendRequests.isNotEmpty() or state.friends.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(MARGIN_MEDIUM_2)
            ) {
                if (state.friendRequests.isNotEmpty()) {
                    // Friend Requests Title
                    item {
                        Text(
                            stringResource(Res.string.friend_requests),
                            fontSize = TEXT_REGULAR_3XX,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = MARGIN_SMALL)
                        )
                    }

                    // Friend Requests List
                    items(state.friendRequests.count()) { index ->
                        FriendRequestItem(
                            friendRequest = state.friendRequests[index],
                            onConfirmFriendRequest = { frId ->
                                onAction(FriendsAction.OnAcceptFriendRequest(frId))
                            },
                            onTapProfile = { userId ->
                                onAction(FriendsAction.OnTapProfile(userId))
                            }
                        )
                    }

                    // Spacer
                    item {
                        Spacer(modifier = Modifier.height(MARGIN_XLARGE))
                    }
                }

                // Friends Title
                if (state.friends.isNotEmpty()){
                    item {
                        Text(
                            stringResource(Res.string.friends),
                            fontSize = TEXT_REGULAR_3XX,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = MARGIN_SMALL)
                        )
                    }

                    // Friends List
                    items(state.friends.count()) { index ->
                        FriendItem(
                            friend = state.friends[index],
                            onTapProfile = { userId ->
                                onAction(FriendsAction.OnTapProfile(userId))
                            }
                        )
                    }
                }


                item {
                    Spacer(modifier = Modifier.height(BOTTOM_SPACING))
                }
            }
        } else {
            // Empty view
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    stringResource(Res.string.friends_empty),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}