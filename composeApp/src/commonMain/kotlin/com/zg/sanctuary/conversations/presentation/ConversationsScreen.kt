package com.zg.sanctuary.conversations.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zg.sanctuary.conversations.presentation.components.ConversationFriendItem
import com.zg.sanctuary.conversations.presentation.components.ConversationItem
import com.zg.sanctuary.core.BOTTOM_SPACING
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_3
import com.zg.sanctuary.core.presentation.components.GeneralAppbar
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.chat
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ConversationsRoute(
    viewModel : ConversationsViewModel,
    onNavigateBack : () -> Unit,
    onNavigateToChat : (Int) -> Unit
){

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest {
            when(it){
                ConversationsEvents.NavigateBack -> {
                    onNavigateBack()
                }
                is ConversationsEvents.NavigateToChat -> {
                    onNavigateToChat(it.userId)
                }
            }
        }
    }

    ConversationsScreen(
        state = state,
        onAction = {
            viewModel.handleAction(it)
        }
    )
}

@Composable
fun ConversationsScreen(
    state : ConversationsState,
    onAction : (ConversationsAction) -> Unit,
){
    Scaffold(
        containerColor = Color.White,
        topBar = {
            GeneralAppbar(
                title = stringResource(Res.string.chat),
                onTapBack = {
                    onAction(ConversationsAction.OnTapBack)
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding).padding(top = MARGIN_MEDIUM_2),) {
            // Friends List
            item {
                LazyRow(contentPadding = PaddingValues(horizontal = MARGIN_MEDIUM_2)){
                    items(state.friends.count()) { index ->
                        val friend = state.friends[index]
                        ConversationFriendItem(
                            friend = friend,
                            onTapFriend = { userId ->
                                onAction(ConversationsAction.OnTapFriend(userId))
                            },
                        )
                    }
                }
            }

            // Spacer
            item{
                Spacer(modifier = Modifier.height(MARGIN_MEDIUM_3))
            }

            // Conversations List
            items(state.conversations.count()) { index->
                // Conversation Item
                ConversationItem(
                    conversation = state.conversations[index],
                    onTapConversation = { userId ->
                        onAction(ConversationsAction.OnTapConversation(userId))
                    },
                    modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2)
                )
            }

            item{
                Spacer(modifier = Modifier.height(BOTTOM_SPACING))
            }
        }
    }
}