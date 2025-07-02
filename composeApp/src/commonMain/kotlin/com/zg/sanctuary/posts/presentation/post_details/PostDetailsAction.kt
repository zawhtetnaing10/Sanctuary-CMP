package com.zg.sanctuary.posts.presentation.post_details

import com.zg.sanctuary.AppRoute

sealed interface PostDetailsAction {
    class OnTapBack() : PostDetailsAction
    class OnTapLike() : PostDetailsAction
    class OnCommentChanged(val comment : String) : PostDetailsAction
    class OnTapPostComment() : PostDetailsAction
}