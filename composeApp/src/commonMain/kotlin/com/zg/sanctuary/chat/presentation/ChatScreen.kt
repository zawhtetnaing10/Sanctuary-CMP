package com.zg.sanctuary.chat.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import com.zg.sanctuary.chat.presentation.components.WriteMessage
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

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest {
            when (it) {
                ChatEvents.NavigateBack -> {
                    onNavigateBack()
                }

                ChatEvents.RemoveFocus -> {
                    focusManager.clearFocus()
                }
            }
        }
    }

    ChatScreen(
        state = state,
        onAction = {
            viewModel.handleAction(it)
        }
    )
}

@Composable
fun ChatScreen(
    state: ChatState,
    onAction: (ChatActions) -> Unit
) {
    Scaffold(
        containerColor = Color.White
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {

            // TODO: - Replace with actual chat message
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(state.chatMessages.count()) {
                    Text(state.chatMessages[it])
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