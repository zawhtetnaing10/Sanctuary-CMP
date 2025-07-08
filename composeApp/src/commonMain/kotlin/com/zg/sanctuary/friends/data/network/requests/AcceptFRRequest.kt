package com.zg.sanctuary.friends.data.network.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AcceptFRRequest(
    @SerialName("friend_request_id")
    val friendRequestId : Int
)