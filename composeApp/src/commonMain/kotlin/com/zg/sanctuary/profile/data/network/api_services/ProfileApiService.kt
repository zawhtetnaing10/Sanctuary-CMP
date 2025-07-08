package com.zg.sanctuary.profile.data.network.api_services

import com.zg.sanctuary.profile.domain.UserProfileDetails
import com.zg.sanctuary.core.data.network.SanctuaryResult
import com.zg.sanctuary.core.domain.SanctuaryError

interface ProfileApiService {
    suspend fun getUserProfile(userId: Int, authToken: String): SanctuaryResult<UserProfileDetails, SanctuaryError>
}