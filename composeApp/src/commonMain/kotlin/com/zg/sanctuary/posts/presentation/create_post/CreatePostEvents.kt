package com.zg.sanctuary.posts.presentation.create_post

sealed interface CreatePostEvents {
    data object NavigateBack : CreatePostEvents
}