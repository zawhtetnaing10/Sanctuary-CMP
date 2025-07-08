package com.zg.sanctuary.posts.data.repositories

import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.comments.domain.Comment
import com.zg.sanctuary.core.data.network.onError
import com.zg.sanctuary.core.data.network.onSuccess
import com.zg.sanctuary.core.persistence.SanctuaryDatabase
import com.zg.sanctuary.posts.data.network.PostApiService
import com.zg.sanctuary.posts.data.network.responses.MetaResponse
import com.zg.sanctuary.posts.domain.Post

class PostRepository(
    private val postApiService: PostApiService,
    private val database: SanctuaryDatabase
) {
    suspend fun getPosts(onSuccess: (MetaResponse) -> Unit, onFailure: (String) -> Unit) {
        val loggedInUser: User? = database.userDao().getLoggedInUser()

        loggedInUser?.let {
            val accessToken = it.getBearerToken()

            postApiService.getPosts(accessToken)
                .onSuccess {
                    onSuccess(it)
                }.onError {
                    onFailure(it.error)
                }
        } ?: run {
            onFailure("User is not logged in.")
        }
    }

    suspend fun getPostsForUser(userId: Int, onSuccess: (List<Post>) -> Unit, onFailure: (String) -> Unit) {
        val loggedInUser = database.userDao().getLoggedInUser()

        loggedInUser?.let {
            val accessToken = it.getBearerToken()

            postApiService.getPostsForUser(userId, accessToken)
                .onSuccess {
                    onSuccess(it)
                }.onError {
                    onFailure(it.error)
                }
        } ?: run {
            onFailure("User is not logged in.")
        }
    }

    suspend fun getMorePosts(page: Int, onSuccess: (MetaResponse?) -> Unit, onFailure: (String) -> Unit) {
        if (page == 0) {
            onSuccess(null)
            return
        }

        val loggedInUser: User? = database.userDao().getLoggedInUser()

        loggedInUser?.let {
            val accessToken = it.getBearerToken()

            postApiService.getMorePosts(page, accessToken)
                .onSuccess {
                    onSuccess(it)
                }.onError {
                    onFailure(it.error)
                }
        } ?: run {
            onFailure("User is not logged in.")
        }
    }

    suspend fun getPostById(postId: Int, onSuccess: (Post) -> Unit, onFailure: (String) -> Unit) {
        val loggedInUser: User? = database.userDao().getLoggedInUser()

        loggedInUser?.let {
            val accessToken = it.getBearerToken()

            postApiService.getPostById(postId = postId, accessToken = accessToken)
                .onSuccess {
                    onSuccess(it)
                }.onError {
                    onFailure(it.error)
                }
        } ?: run {
            onFailure("User is not logged in.")
        }
    }

    suspend fun createPost(content: String, file: ByteArray?, onSuccess: (Post) -> Unit, onFailure: (String) -> Unit) {
        val loggedInUser: User? = database.userDao().getLoggedInUser()

        loggedInUser?.let {
            val accessToken = it.getBearerToken()

            postApiService.createPost(content = content, file = file, accessToken = accessToken)
                .onSuccess {
                    onSuccess(it)
                }.onError {
                    onFailure(it.error)
                }
        } ?: run {
            onFailure("User is not logged in.")
        }
    }

    suspend fun likePost(postId: Int, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val loggedInUser: User? = database.userDao().getLoggedInUser()

        loggedInUser?.let {
            val accessToken = it.getBearerToken()

            postApiService.likePost(postId = postId, accessToken = accessToken)
                .onSuccess {
                    onSuccess()
                }.onError {
                    onFailure(it.error)
                }
        } ?: run {
            onFailure("User is not logged in.")
        }
    }

    suspend fun getCommentsForPost(postId: Int, onSuccess: (List<Comment>) -> Unit, onFailure: (String) -> Unit) {
        val loggedInUser: User? = database.userDao().getLoggedInUser()

        loggedInUser?.let {
            val accessToken = it.getBearerToken()

            postApiService.getCommentsForPost(postId = postId, accessToken = accessToken)
                .onSuccess {
                    onSuccess(it)
                }.onError {
                    onFailure(it.error)
                }
        } ?: run {
            onFailure("User is not logged in.")
        }
    }

    suspend fun createComment(postId: Int, content: String, onSuccess: (Comment) -> Unit, onFailure: (String) -> Unit) {
        val loggedInUser: User? = database.userDao().getLoggedInUser()

        loggedInUser?.let {
            val accessToken = it.getBearerToken()

            postApiService.createComment(postId = postId, content = content, accessToken = accessToken)
                .onSuccess {
                    onSuccess(it)
                }.onError {
                    onFailure(it.error)
                }
        } ?: run {
            onFailure("User is not logged in.")
        }
    }
}