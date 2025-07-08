package com.zg.sanctuary.profile.data.repository

import com.zg.sanctuary.auth.data.network.api_services.AuthApiService
import com.zg.sanctuary.core.data.network.onError
import com.zg.sanctuary.core.data.network.onSuccess
import com.zg.sanctuary.core.persistence.SanctuaryDatabase
import com.zg.sanctuary.profile.data.network.api_services.ProfileApiService
import com.zg.sanctuary.profile.domain.UserProfileDetails

class ProfileRepository(
    private val profileApiService: ProfileApiService,
    private val database: SanctuaryDatabase
) {
    suspend fun getUserDetails(userId: Int, onSuccess: (UserProfileDetails) -> Unit, onFailure: (String) -> Unit) {
        val loggedInUser = database.userDao().getLoggedInUser()

        loggedInUser?.let {
            profileApiService.getUserProfile(userId, loggedInUser.getBearerToken())
                .onSuccess {
                    onSuccess(it)
                }.onError {
                    onFailure(it.error)
                }
        } ?: run {
            onFailure("User is not logged in.")
        }
    }
}