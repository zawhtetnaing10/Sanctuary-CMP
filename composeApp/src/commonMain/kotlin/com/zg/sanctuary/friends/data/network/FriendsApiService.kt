package com.zg.sanctuary.friends.data.network

import com.zg.sanctuary.core.data.network.SanctuaryResult
import com.zg.sanctuary.core.domain.SanctuaryError
import com.zg.sanctuary.friends.domain.FriendRequest

interface FriendsApiService {
    suspend fun getAllFriendRequests(accessToken: String): SanctuaryResult<List<FriendRequest>, SanctuaryError>
    suspend fun getAllFriends(accessToken: String): SanctuaryResult<List<FriendRequest>, SanctuaryError>
    suspend fun acceptFriendRequest(frId: Int, accessToken: String): SanctuaryResult<Unit, SanctuaryError>
    suspend fun createFriendRequest(senderId: Int, receiverId: Int, accessToken: String): SanctuaryResult<FriendRequest, SanctuaryError>
}