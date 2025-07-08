package com.zg.sanctuary.auth.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zg.sanctuary.core.utils.DateUtils
import com.zg.sanctuary.interests.domain.Interest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class User(
    @SerialName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerialName("email")
    val email: String,
    @SerialName("user_name")
    val userName: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("profile_image_url")
    val profileImageUrl: String?,
    @SerialName("dob")
    val dob: String?,
    @SerialName("access_token")
    var accessToken: String?,
    @SerialName("interests")
    val interests: List<Interest>?
) {
    fun getBearerToken(): String = "Bearer $accessToken"

    fun getJoinedDate(): String {
        return DateUtils.getYear(createdAt)
    }
}