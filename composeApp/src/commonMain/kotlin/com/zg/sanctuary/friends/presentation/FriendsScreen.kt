package com.zg.sanctuary.friends.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.zg.sanctuary.core.BOTTOM_SPACING
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_SMALL
import com.zg.sanctuary.core.MARGIN_XLARGE
import com.zg.sanctuary.core.MARGIN_XXLARGE
import com.zg.sanctuary.core.TEXT_REGULAR_3XX
import com.zg.sanctuary.core.TEXT_REGULAR_4X
import com.zg.sanctuary.friends.presentation.components.FriendItem
import com.zg.sanctuary.friends.presentation.components.FriendRequestItem
import org.jetbrains.compose.resources.stringResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.friend_requests
import sanctuary.composeapp.generated.resources.friends

@Composable
fun FriendsRoute() {
    FriendsScreen()
}

@Composable
fun FriendsScreen() {
    Scaffold(containerColor = Color.White) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(MARGIN_MEDIUM_2)
                .padding(bottom = BOTTOM_SPACING)
        ) {
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
            items(3) {
                FriendRequestItem(
                    onConfirmFriendRequest = {}
                )
            }

            // Spacer
            item {
                Spacer(modifier = Modifier.height(MARGIN_XLARGE))
            }

            // Friends Title
            item {
                Text(
                    stringResource(Res.string.friends),
                    fontSize = TEXT_REGULAR_3XX,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = MARGIN_SMALL)
                )
            }

            // Friends List
            items(20) {
                FriendItem()
            }
        }
    }
}