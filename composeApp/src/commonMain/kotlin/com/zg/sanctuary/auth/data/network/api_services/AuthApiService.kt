package com.zg.sanctuary.auth.data.network.api_services

import com.zg.sanctuary.core.data.network.SanctuaryResult
import com.zg.sanctuary.core.domain.SanctuaryError
import com.zg.sanctuary.auth.domain.User

interface AuthApiService {
    suspend fun login(email : String, password : String) : SanctuaryResult<User, SanctuaryError>
}