package com.zg.sanctuary

import androidx.compose.runtime.Composable
import com.zg.sanctuary.posts.presentation.components.CreatePostView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview()
@Composable
fun CreatePostViewPreview() {
    CreatePostView(
        onCreateConfirmed = {}
    )
}
