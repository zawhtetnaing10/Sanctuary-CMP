package com.zg.sanctuary.posts.presentation.post_list

sealed interface PostListAction {
    class OnTapSearch() : PostListAction
    class OnTapCreatePost() : PostListAction
    data class OnTapPost(val id: Int) : PostListAction
    data class OnTapLike(val id: Int) : PostListAction
    data class OnTapComment(val id: Int) : PostListAction
    data class OnTapShare(val id: Int) : PostListAction
    class OnPostListScreenReached() : PostListAction
    class OnListEndReached() : PostListAction
    class OnErrorDialogDismissed() : PostListAction
}