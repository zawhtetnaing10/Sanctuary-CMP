package com.zg.sanctuary.posts.data.network.responses

import com.zg.sanctuary.posts.domain.Post
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaResponse(
    @SerialName("data")
    val data : List<Post>,
    @SerialName("meta")
    val meta : Meta
)

@Serializable
data class Meta(
    @SerialName("current_page")
    val currentPage : Int,
    @SerialName("next_page")
    val nextPage : Int,
    @SerialName("next_page_url")
    val nextPageUrl : String
)