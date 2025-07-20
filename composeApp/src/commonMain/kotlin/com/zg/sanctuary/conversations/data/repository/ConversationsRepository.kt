package com.zg.sanctuary.conversations.data.repository

import com.zg.sanctuary.conversations.data.network.ConversationsApiService
import com.zg.sanctuary.conversations.domain.ConversationResponse
import com.zg.sanctuary.core.data.network.onError
import com.zg.sanctuary.core.data.network.onSuccess
import com.zg.sanctuary.core.persistence.SanctuaryDatabase

class ConversationsRepository(
    val conversationsApiService: ConversationsApiService,
    val database: SanctuaryDatabase
) {
    suspend fun getConversations(onSuccess : (List<ConversationResponse>) -> Unit, onFailure : (String) -> Unit){

        val loggedInUser = database.userDao().getLoggedInUser()

        loggedInUser?.let {
            val bearerToken = it.getBearerToken()
            conversationsApiService.getConversationsForUser(
                accessToken = bearerToken
            ).onSuccess { conversationResponse ->
                onSuccess(conversationResponse)
            }.onError {
                onFailure(it.error)
            }
        }
    }
}