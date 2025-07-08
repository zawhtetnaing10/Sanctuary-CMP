package com.zg.sanctuary.friends.data.repositories

import com.zg.sanctuary.core.data.network.onError
import com.zg.sanctuary.core.data.network.onSuccess
import com.zg.sanctuary.core.persistence.SanctuaryDatabase
import com.zg.sanctuary.friends.data.network.FriendsApiService
import com.zg.sanctuary.friends.domain.FriendRequest

class FriendsRepository(
    val apiService: FriendsApiService,
    val database: SanctuaryDatabase
) {
    suspend fun getFriendRequests(onSuccess: (List<FriendRequest>) -> Unit, onFailure: (String) -> Unit) {
        val loggedInUser = database.userDao().getLoggedInUser()
        loggedInUser?.let {
            val accessToken = loggedInUser.getBearerToken()
            apiService.getAllFriendRequests(accessToken)
                .onSuccess {
                    onSuccess(it)
                }.onError {
                    onFailure(it.error)
                }
        } ?: run {
            onFailure("User not logged in.")
        }
    }

    suspend fun getFriends(onSuccess: (List<FriendRequest>) -> Unit, onFailure: (String) -> Unit) {
        val loggedInUser = database.userDao().getLoggedInUser()
        loggedInUser?.let {
            val accessToken = loggedInUser.getBearerToken()
            apiService.getAllFriends(accessToken)
                .onSuccess {
                    onSuccess(it)
                }.onError {
                    onFailure(it.error)
                }
        } ?: run {
            onFailure("User not logged in.")
        }
    }

    suspend fun createFriendRequest(senderId: Int, receiverId: Int, onSuccess: (FriendRequest) -> Unit, onFailure: (String) -> Unit) {
        val loggedInUser = database.userDao().getLoggedInUser()
        loggedInUser?.let {
            val accessToken = loggedInUser.getBearerToken()
            apiService.createFriendRequest(senderId = senderId, receiverId = receiverId, accessToken = accessToken)
                .onSuccess {
                    onSuccess(it)
                }.onError {
                    onFailure(it.error)
                }
        } ?: run {
            onFailure("User not logged in.")
        }
    }

    suspend fun acceptFriendRequest(frId: Int, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val loggedInUser = database.userDao().getLoggedInUser()
        loggedInUser?.let {
            val accessToken = loggedInUser.getBearerToken()
            apiService.acceptFriendRequest(frId = frId, accessToken = accessToken)
                .onSuccess {
                    onSuccess()
                }.onError {
                    onFailure(it.error)
                }
        } ?: run {
            onFailure("User not logged in.")
        }
    }
}