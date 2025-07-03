package com.zg.sanctuary.posts.presentation.create_post

sealed interface CreatePostAction {
    data class OnChangeContent(val content: String) : CreatePostAction

    data object OnTapCreatePost : CreatePostAction

    data object OnTapBack : CreatePostAction

    data object OnNavigateBack : CreatePostAction

    data object OnDeleteImage : CreatePostAction

    data object OnErrorDialogDismissed : CreatePostAction

    data class OnChangeImage(val image: ByteArray) : CreatePostAction {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as OnChangeImage

            if (!image.contentEquals(other.image)) return false

            return true
        }

        override fun hashCode(): Int {
            return image.contentHashCode()
        }
    }
}