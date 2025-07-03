package com.zg.sanctuary.posts.presentation.create_post

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.auth.presentation.login.LoginActions
import com.zg.sanctuary.core.MARGIN_MEDIUM
import com.zg.sanctuary.core.MARGIN_MEDIUM_2
import com.zg.sanctuary.core.MARGIN_XXLARGE
import com.zg.sanctuary.core.TEXT_REGULAR_2X
import com.zg.sanctuary.core.presentation.components.dialogs.ErrorDialog
import com.zg.sanctuary.core.presentation.components.dialogs.LoadingDialog
import com.zg.sanctuary.posts.presentation.components.CreatePostAppbar
import com.zg.sanctuary.posts.presentation.components.CreatePostInput
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.image_loading_error
import sanctuary.composeapp.generated.resources.loading_skeleton

@Composable
fun CreatePostRoute(
    viewModel: CreatePostViewModel,
    onNavigateBack: () -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest {
            when (it) {
                CreatePostEvents.NavigateBack -> {
                    onNavigateBack()
                }
            }
        }
    }

    CreatePostScreen(
        state = state,
        onAction = {
            viewModel.handleAction(it)
        }
    )
}

@Composable
fun CreatePostScreen(
    state: CreatePostState,
    onAction: (CreatePostAction) -> Unit
) {

    if (state.isLoading) {
        LoadingDialog(onDismissRequest = {})
    }

    if (state.error.isNotEmpty()) {
        ErrorDialog(
            onDismissRequest = {
                onAction(CreatePostAction.OnErrorDialogDismissed)
            },
            message = state.error
        )
    }

    Scaffold(
        topBar = {
            CreatePostAppbar(
                onTapBack = {
                    onAction(CreatePostAction.OnNavigateBack)
                },
                onTapPost = {
                    onAction(CreatePostAction.OnTapCreatePost)
                }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {

            // Logged in user
            if(state.loggedInUser != null)
                item {
                    CreatePostLoggedInUser(
                        user = state.loggedInUser
                    )
                }

            item {
                Spacer(modifier = Modifier.height(MARGIN_MEDIUM))
            }

            item {
                // Input box
                CreatePostInput(
                    content = state.content,
                    onContentChanged = {
                        onAction(CreatePostAction.OnChangeContent(it))
                    },
                    onImagePicked = {
                        onAction(CreatePostAction.OnChangeImage(it))
                    },
                    onDeleteImage = {
                        onAction(CreatePostAction.OnDeleteImage)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(MARGIN_XXLARGE))
            }
        }
    }
}

@Composable
fun CreatePostLoggedInUser(user: User) {
    Row(
        modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_MEDIUM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.profileImageUrl,
            contentDescription = "User profile image",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(Res.drawable.loading_skeleton),
            error = painterResource(Res.drawable.image_loading_error),
            modifier = Modifier
                .size(MARGIN_XXLARGE)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(MARGIN_MEDIUM))
        Text(
            text = user.fullName,
            fontSize = TEXT_REGULAR_2X,
            fontWeight = FontWeight.Bold,
        )
    }
}