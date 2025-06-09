package com.zg.sanctuary.auth.data.network.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmailAndPasswordRequest(
    @SerialName("email")
    val email : String,
    @SerialName("password")
    val password : String,
)