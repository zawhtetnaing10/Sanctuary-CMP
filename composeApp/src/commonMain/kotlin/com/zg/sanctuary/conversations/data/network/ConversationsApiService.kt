package com.zg.sanctuary.conversations.data.network

import com.zg.sanctuary.conversations.domain.ConversationResponse
import com.zg.sanctuary.core.data.network.SanctuaryResult
import com.zg.sanctuary.core.domain.SanctuaryError

interface ConversationsApiService {
    suspend fun getConversationsForUser(accessToken: String): SanctuaryResult<List<ConversationResponse>, SanctuaryError>
}