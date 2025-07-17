package com.zg.sanctuary.conversations.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zg.sanctuary.conversations.presentation.components.ConversationFriendItem
import com.zg.sanctuary.conversations.presentation.components.ConversationItem
import com.zg.sanctuary.core.BOTTOM_SPACING
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_MEDIUM_3
import com.zg.sanctuary.core.presentation.components.GeneralAppbar
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.chat

@Composable
fun ConversationsRoute(

){
    ConversationsScreen()
}

@Composable
fun ConversationsScreen(){
    Scaffold(
        containerColor = Color.White,
        topBar = {
            GeneralAppbar(
                title = stringResource(Res.string.chat),
                onTapBack = {
                    // TODO: - Navigate back
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding).padding(top = MARGIN_MEDIUM_2),) {
            // Friends List
            item {
                LazyRow(contentPadding = PaddingValues(horizontal = MARGIN_MEDIUM_2)){
                    items(20) {
                        ConversationFriendItem(
                            onTapFriend = {
                                // TODO: - Notify view model
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
            items(50) {
                // Conversation Item
                ConversationItem(
                    onTapConversation = {
                        // TODO: - Notify View Model
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