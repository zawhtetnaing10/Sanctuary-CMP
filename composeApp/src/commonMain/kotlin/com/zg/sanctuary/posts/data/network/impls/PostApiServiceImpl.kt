package com.zg.sanctuary.posts.data.network.impls

import com.zg.sanctuary.comments.domain.Comment
import com.zg.sanctuary.core.data.network.ENDPOINT_COMMENTS
import com.zg.sanctuary.core.data.network.ENDPOINT_POSTS
import com.zg.sanctuary.core.data.network.ENDPOINT_POST_LIKE
import com.zg.sanctuary.core.data.network.HttpClientProvider
import com.zg.sanctuary.core.data.network.SanctuaryResult
import com.zg.sanctuary.core.data.network.safeCall
import com.zg.sanctuary.core.domain.SanctuaryError
import com.zg.sanctuary.core.utils.ImageFormatDetector
import com.zg.sanctuary.posts.data.network.PostApiService
import com.zg.sanctuary.posts.data.network.requests.CreateCommentRequest
import com.zg.sanctuary.posts.data.network.requests.RequestWithPostId
import com.zg.sanctuary.posts.data.network.responses.MetaResponse
import com.zg.sanctuary.posts.domain.Post
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.datetime.Clock

class PostApiServiceImpl : PostApiService {
    override suspend fun getPosts(accessToken: String): SanctuaryResult<MetaResponse, SanctuaryError> {
        return safeCall<MetaResponse> {
            HttpClientProvider.httpClient.get(ENDPOINT_POSTS) {
                header(HttpHeaders.Authorization, accessToken)
            }
        }
    }

    override suspend fun getMorePosts(
        page: Int,
        accessToken: String
    ): SanctuaryResult<MetaResponse, SanctuaryError> {
        return safeCall<MetaResponse> {
            HttpClientProvider.httpClient.get("$ENDPOINT_POSTS?page=$page") {
                header(HttpHeaders.Authorization, accessToken)
            }
        }
    }

    override suspend fun getPostById(
        postId: Int,
        accessToken: String
    ): SanctuaryResult<Post, SanctuaryError> {
        return safeCall<Post> {
            HttpClientProvider.httpClient.get("$ENDPOINT_POSTS/${postId}") {
                header(HttpHeaders.Authorization, accessToken)
            }
        }
    }

    override suspend fun createPost(
        content: String,
        file: ByteArray?,
        accessToken: String
    ): SanctuaryResult<Post, SanctuaryError> {

        var fileName: String? = null
        var contentType: ContentType? = null

        if (file != null) {
            // Get Image format
            contentType = ImageFormatDetector.detectImageContentType(file)
                ?: return SanctuaryResult.Failure(SanctuaryError(error = "Unknown image format"))
            // Craft File Name
            val imageFormatExtension = ImageFormatDetector.getDefaultExtension(contentType)
            fileName = "image_${Clock.System.now().toEpochMilliseconds()}.${imageFormatExtension}"
        }

        return safeCall<Post> {
            HttpClientProvider.httpClient.submitFormWithBinaryData(url = ENDPOINT_POSTS, formData {
                // Profile Image
                if (file != null) {
                    append(
                        key = "file",
                        value = file,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, contentType.toString())
                            append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                        }
                    )
                }

                append("content", content)
            }) {
                header(HttpHeaders.Authorization, accessToken)
            }
        }
    }

    override suspend fun likePost(
        postId: Int,
        accessToken: String
    ): SanctuaryResult<Unit, SanctuaryError> {
        val request = RequestWithPostId(
            postId = postId
        )
        return safeCall<Unit> {
            HttpClientProvider.httpClient.post(ENDPOINT_POST_LIKE) {

                header(HttpHeaders.Authorization, accessToken)

                setBody(request)
            }
        }
    }

    override suspend fun getCommentsForPost(
        postId: Int,
        accessToken: String
    ): SanctuaryResult<List<Comment>, SanctuaryError> {
        return safeCall<List<Comment>> {
            HttpClientProvider.httpClient.get("$ENDPOINT_COMMENTS?post_id=$postId") {
                header(HttpHeaders.Authorization, accessToken)
            }
        }
    }

    override suspend fun createComment(
        postId: Int,
        content: String,
        accessToken: String
    ): SanctuaryResult<Comment, SanctuaryError> {
        val request = CreateCommentRequest(
            postId = postId,
            content = content
        )
        return safeCall<Comment> {
            HttpClientProvider.httpClient.post(ENDPOINT_COMMENTS) {

                header(HttpHeaders.Authorization, accessToken)

                setBody(request)
            }
        }
    }
}