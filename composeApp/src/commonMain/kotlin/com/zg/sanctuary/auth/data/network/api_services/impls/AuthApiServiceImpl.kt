package com.zg.sanctuary.auth.data.network.api_services.impls

import com.zg.sanctuary.auth.data.network.api_services.AuthApiService
import com.zg.sanctuary.auth.data.network.requests.EmailAndPasswordRequest
import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.core.data.network.ENDPOINT_LOGIN
import com.zg.sanctuary.core.data.network.ENDPOINT_REGISTER
import com.zg.sanctuary.core.data.network.ENDPOINT_UPDATE_USER
import com.zg.sanctuary.core.data.network.HttpClientProvider
import com.zg.sanctuary.core.data.network.SanctuaryResult
import com.zg.sanctuary.core.data.network.safeCall
import com.zg.sanctuary.core.domain.SanctuaryError
import com.zg.sanctuary.core.utils.ImageFormatDetector
import com.zg.sanctuary.interests.domain.Interest
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.datetime.Clock
import kotlin.time.ExperimentalTime

class AuthApiServiceImpl : AuthApiService {
    override suspend fun login(
        email: String,
        password: String
    ): SanctuaryResult<User, SanctuaryError> {
        val request = EmailAndPasswordRequest(
            email = email,
            password = password
        )
        return safeCall<User> {
            HttpClientProvider.httpClient.post(ENDPOINT_LOGIN) {
                setBody(request)
            }
        }
    }

    override suspend fun createAccount(
        email: String,
        password: String
    ): SanctuaryResult<User, SanctuaryError> {
        val request = EmailAndPasswordRequest(
            email = email,
            password = password
        )
        return safeCall<User> {
            HttpClientProvider.httpClient.post(ENDPOINT_REGISTER) {
                setBody(request)
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun updateUser(
        profileImage: ByteArray,
        fullName: String,
        userName: String,
        dob: String,
        chosenInterests: List<Interest>,
        authToken: String
    ): SanctuaryResult<User, SanctuaryError> {
        // Get Image format
        val contentType: ContentType = ImageFormatDetector.detectImageContentType(profileImage)
            ?: return SanctuaryResult.Failure(SanctuaryError(error = "Unknown image format"))

        val imageFormatExtension = ImageFormatDetector.getDefaultExtension(contentType)
        val fileName = "profile_image_${Clock.System.now().toEpochMilliseconds()}.${imageFormatExtension}"

        val chosenInterestIds = chosenInterests.map { it.id }

        return safeCall<User> {
            HttpClientProvider.httpClient.submitFormWithBinaryData(url = ENDPOINT_UPDATE_USER, formData {
                // Profile Image
                append(
                    key = "profile",
                    value = profileImage,
                    headers = Headers.build {
                        append(HttpHeaders.ContentType, contentType.toString())
                        append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                    }
                )

                // Interest Ids
                chosenInterestIds.forEach { id ->
                    append("ids", id)
                }

                append("full_name", fullName)
                append("user_name", userName)
                append("dob", dob)
            }) {
                header(HttpHeaders.Authorization, authToken)
            }
        }
    }
}