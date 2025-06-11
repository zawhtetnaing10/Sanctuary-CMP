package com.zg.sanctuary.auth.data.network.api_services

import com.zg.sanctuary.core.data.network.SanctuaryResult
import com.zg.sanctuary.core.domain.SanctuaryError
import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.interests.domain.Interest

interface AuthApiService {
    suspend fun login(email: String, password: String): SanctuaryResult<User, SanctuaryError>
    suspend fun createAccount(email: String, password: String): SanctuaryResult<User, SanctuaryError>
    suspend fun updateUser(
        profileImage: ByteArray,
        fullName: String,
        userName: String,
        dob: String,
        chosenInterests: List<Interest>,
        authToken: String,
    ): SanctuaryResult<User, SanctuaryError>
}