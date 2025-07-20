package com.zg.sanctuary.conversations.data.network.impls

import com.zg.sanctuary.conversations.data.network.ConversationsApiService
import com.zg.sanctuary.conversations.domain.ConversationResponse
import com.zg.sanctuary.core.data.network.ENDPOINT_GET_CONVERSATIONS
import com.zg.sanctuary.core.data.network.HttpClientProvider
import com.zg.sanctuary.core.data.network.SanctuaryResult
import com.zg.sanctuary.core.data.network.safeCall
import com.zg.sanctuary.core.domain.SanctuaryError
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

class ConversationsApiServiceImpl : ConversationsApiService {
    override suspend fun getConversationsForUser(accessToken: String): SanctuaryResult<List<ConversationResponse>, SanctuaryError> {
        return safeCall {
            HttpClientProvider.httpClient.get(ENDPOINT_GET_CONVERSATIONS) {
                header(HttpHeaders.Authorization, accessToken)
            }
        }
    }
}