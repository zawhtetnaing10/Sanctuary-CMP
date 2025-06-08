package com.zg.sanctuary.interests.data.network.api_services.impls

import com.zg.sanctuary.core.domain.SanctuaryError
import com.zg.sanctuary.interests.domain.Interest
import com.zg.sanctuary.core.network.ENDPOINT_GET_ALL_INTERESTS
import com.zg.sanctuary.core.network.HttpClientProvider
import com.zg.sanctuary.core.network.SanctuaryResult
import com.zg.sanctuary.core.network.safeCall
import com.zg.sanctuary.interests.data.network.api_services.InterestsApiService
import io.ktor.client.request.get

// TODO:- Set up Koin dependency injection for api services
class InterestApiServiceImpl : InterestsApiService {
    override suspend fun getAllInterests(): SanctuaryResult<List<Interest>, SanctuaryError> {
        return safeCall<List<Interest>> {
            HttpClientProvider.httpClient.get(ENDPOINT_GET_ALL_INTERESTS)
        }
    }
}