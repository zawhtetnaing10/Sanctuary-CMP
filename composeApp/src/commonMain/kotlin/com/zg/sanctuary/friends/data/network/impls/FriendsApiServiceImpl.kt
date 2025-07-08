package com.zg.sanctuary.friends.data.network.impls

import com.zg.sanctuary.core.data.network.ENDPOINT_ACCEPT_FRIEND_REQUEST
import com.zg.sanctuary.core.data.network.ENDPOINT_FRIENDS
import com.zg.sanctuary.core.data.network.ENDPOINT_FRIEND_REQUESTS
import com.zg.sanctuary.core.data.network.HttpClientProvider
import com.zg.sanctuary.core.data.network.SanctuaryResult
import com.zg.sanctuary.core.data.network.safeCall
import com.zg.sanctuary.core.domain.SanctuaryError
import com.zg.sanctuary.friends.data.network.FriendsApiService
import com.zg.sanctuary.friends.data.network.requests.AcceptFRRequest
import com.zg.sanctuary.friends.data.network.requests.FRRequest
import com.zg.sanctuary.friends.domain.FriendRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders

class FriendsApiServiceImpl : FriendsApiService {
    override suspend fun getAllFriendRequests(accessToken: String): SanctuaryResult<List<FriendRequest>, SanctuaryError> {
        return safeCall<List<FriendRequest>> {
            HttpClientProvider.httpClient.get(ENDPOINT_FRIEND_REQUESTS) {
                header(HttpHeaders.Authorization, accessToken)
            }
        }
    }

    override suspend fun getAllFriends(accessToken: String): SanctuaryResult<List<FriendRequest>, SanctuaryError> {
        return safeCall<List<FriendRequest>> {
            HttpClientProvider.httpClient.get(ENDPOINT_FRIENDS) {
                header(HttpHeaders.Authorization, accessToken)
            }
        }
    }

    override suspend fun acceptFriendRequest(
        frId: Int,
        accessToken: String
    ): SanctuaryResult<Unit, SanctuaryError> {
        val request = AcceptFRRequest(
            friendRequestId = frId
        )

        return safeCall<Unit> {
            HttpClientProvider.httpClient.put(ENDPOINT_ACCEPT_FRIEND_REQUEST) {
                header(HttpHeaders.Authorization, accessToken)
                setBody(request)
            }
        }
    }

    override suspend fun createFriendRequest(
        senderId: Int,
        receiverId: Int,
        accessToken: String
    ): SanctuaryResult<FriendRequest, SanctuaryError> {
        val request = FRRequest(
            senderId = senderId,
            receiverId = receiverId
        )

        return safeCall<FriendRequest> {
            HttpClientProvider.httpClient.post(ENDPOINT_FRIEND_REQUESTS) {
                header(HttpHeaders.Authorization, accessToken)
                setBody(request)
            }
        }
    }
}