package com.zg.sanctuary.interests.data.network.api_services

import com.zg.sanctuary.core.domain.SanctuaryError
import com.zg.sanctuary.core.network.SanctuaryResult
import com.zg.sanctuary.interests.domain.Interest

interface InterestsApiService {
    suspend fun getAllInterests() : SanctuaryResult<List<Interest>, SanctuaryError>
}