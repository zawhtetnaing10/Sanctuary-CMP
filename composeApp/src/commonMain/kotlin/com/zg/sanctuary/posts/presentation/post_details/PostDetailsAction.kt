package com.zg.sanctuary.posts.presentation.post_details

sealed interface PostDetailsAction {
    class OnTapBack() : PostDetailsAction
    class OnTapLike() : PostDetailsAction
    class OnCommentChanged(val comment: String) : PostDetailsAction
    class OnTapPostComment() : PostDetailsAction
    data class OnTapUserProfile(val userId: Int) : PostDetailsAction
}