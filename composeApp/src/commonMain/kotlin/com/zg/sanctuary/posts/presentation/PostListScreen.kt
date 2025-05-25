package com.zg.sanctuary.posts.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.zg.sanctuary.posts.presentation.components.PostListAppbar

@Composable
fun PostListRoute() {
    PostListScreen()
}

@Composable
fun PostListScreen() {
    Scaffold(
        // Appbar
        topBar = {
            PostListAppbar()
        }
    ) {

    }
}