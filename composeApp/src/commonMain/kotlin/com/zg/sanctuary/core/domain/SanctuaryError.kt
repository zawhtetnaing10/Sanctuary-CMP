package com.zg.sanctuary.core.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SanctuaryError(
    var errorType : SanctuaryErrorEnums? = null,
    @SerialName("error")
    val error: String
)