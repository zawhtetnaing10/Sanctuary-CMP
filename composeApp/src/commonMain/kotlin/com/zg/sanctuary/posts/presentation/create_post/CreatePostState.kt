package com.zg.sanctuary.posts.presentation.create_post

import com.zg.sanctuary.auth.domain.User

data class CreatePostState(
    val content: String = "",
    val image: ByteArray = byteArrayOf(),
    val error: String = "",
    val isLoading: Boolean = false,
    val loggedInUser: User? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as CreatePostState

        if (isLoading != other.isLoading) return false
        if (content != other.content) return false
        if (!image.contentEquals(other.image)) return false
        if (error != other.error) return false
        if (loggedInUser != other.loggedInUser) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + image.contentHashCode()
        result = 31 * result + error.hashCode()
        result = 31 * result + (loggedInUser?.hashCode() ?: 0)
        return result
    }
}