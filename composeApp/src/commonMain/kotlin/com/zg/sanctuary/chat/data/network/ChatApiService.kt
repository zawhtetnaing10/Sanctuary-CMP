package com.zg.sanctuary.chat.data.network

import com.zg.sanctuary.chat.domain.ChatHistoryResponse
import com.zg.sanctuary.core.data.network.SanctuaryResult
import com.zg.sanctuary.core.domain.SanctuaryError

interface ChatApiService {
    suspend fun getChatHistory(accessToken : String, otherUserId : Int) : SanctuaryResult<ChatHistoryResponse, SanctuaryError>
}