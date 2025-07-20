package com.zg.sanctuary.chat.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import com.zg.sanctuary.chat.presentation.components.ChatAppbar
import com.zg.sanctuary.chat.presentation.components.ChatItem
import com.zg.sanctuary.chat.presentation.components.WriteMessage
import com.zg.sanctuary.core.BOTTOM_SPACING_CHAT
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.core.WRITE_COMMENT_SECTION_HEIGHT
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChatRoute(
    viewModel: ChatViewModel,
    onNavigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    val chatListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest {
            when (it) {
                ChatEvents.NavigateBack -> {
                    onNavigateBack()
                }

                ChatEvents.RemoveFocus -> {
                    focusManager.clearFocus()
                }

                is ChatEvents.ScrollToBottom -> {
                    chatListState.animateScrollToItem(index = it.lastIndex)
                }
            }
        }
    }

    ChatScreen(
        state = state,
        chatListState = chatListState,
        onAction = {
            viewModel.handleAction(it)
        }
    )
}

@Composable
fun ChatScreen(
    state: ChatState,
    chatListState : LazyListState,
    onAction: (ChatActions) -> Unit
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            if(state.otherUser != null){
                ChatAppbar(
                    otherUser = state.otherUser,
                    onTapBack = {
                        onAction(ChatActions.OnTapBack)
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = chatListState,
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(horizontal = MARGIN_MEDIUM_2)
            ) {
                items(state.chatMessages.count()) { index ->
                    if (state.loggedInUser != null && state.otherUser != null) {
                        ChatItem(
                            chatMessage = state.chatMessages[index],
                            loggedInUser = state.loggedInUser,
                            otherUser = state.otherUser
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(BOTTOM_SPACING_CHAT))
                }
            }

            if (state.loggedInUser != null)
                Surface(
                    color = Color.White,
                    shadowElevation = MARGIN_SMALL,
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).height(WRITE_COMMENT_SECTION_HEIGHT)
                ) {
                    WriteMessage(
                        loggedInUser = state.loggedInUser,
                        comment = state.currentMessage,
                        onMessageChanged = {
                            onAction(ChatActions.OnChangeMessage(it))
                        },
                        onMessageConfirmed = {
                            onAction(ChatActions.OnSendMessage)
                        }
                    )
                }
        }
    }
}