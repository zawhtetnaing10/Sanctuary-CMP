package com.zg.sanctuary.profile.data.network.api_services.impls

import com.zg.sanctuary.profile.domain.UserProfileDetails
import com.zg.sanctuary.core.data.network.ENDPOINT_GET_USER_PROFILE
import com.zg.sanctuary.core.data.network.HttpClientProvider
import com.zg.sanctuary.core.data.network.SanctuaryResult
import com.zg.sanctuary.core.data.network.safeCall
import com.zg.sanctuary.core.domain.SanctuaryError
import com.zg.sanctuary.profile.data.network.api_services.ProfileApiService
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

class ProfileApiServiceImpl : ProfileApiService {
    override suspend fun getUserProfile(userId: Int, authToken: String): SanctuaryResult<UserProfileDetails, SanctuaryError> {
        return safeCall<UserProfileDetails> {
            HttpClientProvider.httpClient.get("$ENDPOINT_GET_USER_PROFILE/$userId") {
                header(HttpHeaders.Authorization, authToken)
            }
        }
    }
}