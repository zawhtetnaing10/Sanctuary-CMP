package com.zg.sanctuary.auth.data.network.api_services.impls

import com.zg.sanctuary.auth.data.network.api_services.AuthApiService
import com.zg.sanctuary.auth.data.network.requests.EmailAndPasswordRequest
import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.core.data.network.ENDPOINT_LOGIN
import com.zg.sanctuary.core.data.network.ENDPOINT_REGISTER
import com.zg.sanctuary.core.data.network.HttpClientProvider
import com.zg.sanctuary.core.data.network.SanctuaryResult
import com.zg.sanctuary.core.data.network.safeCall
import com.zg.sanctuary.core.domain.SanctuaryError
import io.ktor.client.request.post
import io.ktor.client.request.setBody

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
}