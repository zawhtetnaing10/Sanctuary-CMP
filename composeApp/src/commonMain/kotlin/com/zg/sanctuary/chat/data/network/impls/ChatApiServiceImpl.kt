package com.zg.sanctuary.chat.data.network.impls

import com.zg.sanctuary.chat.data.network.ChatApiService
import com.zg.sanctuary.chat.domain.ChatHistoryResponse
import com.zg.sanctuary.core.data.network.ENDPOINT_GET_CHAT_MESSAGE_HISTORY
import com.zg.sanctuary.core.data.network.HttpClientProvider
import com.zg.sanctuary.core.data.network.SanctuaryResult
import com.zg.sanctuary.core.data.network.safeCall
import com.zg.sanctuary.core.domain.SanctuaryError
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

class ChatApiServiceImpl : ChatApiService {
    override suspend fun getChatHistory(
        accessToken: String,
        otherUserId: Int
    ): SanctuaryResult<ChatHistoryResponse, SanctuaryError> {
        return safeCall {
            HttpClientProvider.httpClient.get("$ENDPOINT_GET_CHAT_MESSAGE_HISTORY?other_user_id=$otherUserId") {
                header(HttpHeaders.Authorization, accessToken)
            }
        }
    }
}