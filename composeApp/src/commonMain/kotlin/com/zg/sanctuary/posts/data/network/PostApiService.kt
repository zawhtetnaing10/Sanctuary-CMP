package com.zg.sanctuary.posts.data.network

import com.zg.sanctuary.comments.domain.Comment
import com.zg.sanctuary.core.data.network.SanctuaryResult
import com.zg.sanctuary.core.domain.SanctuaryError
import com.zg.sanctuary.posts.data.network.responses.MetaResponse
import com.zg.sanctuary.posts.domain.Post

interface PostApiService {
    suspend fun getPosts(accessToken: String): SanctuaryResult<MetaResponse, SanctuaryError>
    suspend fun getPostsForUser(userId: Int, accessToken: String): SanctuaryResult<List<Post>, SanctuaryError>
    suspend fun getMorePosts(page: Int, accessToken: String): SanctuaryResult<MetaResponse, SanctuaryError>
    suspend fun getPostById(postId: Int, accessToken: String): SanctuaryResult<Post, SanctuaryError>
    suspend fun createPost(content: String, file: ByteArray?, accessToken: String): SanctuaryResult<Post, SanctuaryError>
    suspend fun likePost(postId: Int, accessToken: String): SanctuaryResult<Unit, SanctuaryError>
    suspend fun getCommentsForPost(postId: Int, accessToken: String): SanctuaryResult<List<Comment>, SanctuaryError>
    suspend fun createComment(postId: Int, content: String, accessToken: String): SanctuaryResult<Comment, SanctuaryError>
}