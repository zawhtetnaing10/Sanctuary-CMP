package com.zg.sanctuary.friends.data.network.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FRRequest(
    @SerialName("sender_id")
    val senderId : Int,
    @SerialName("receiver_id")
    val receiverId : Int
)