package com.zg.sanctuary.auth.data.repositories

import com.zg.sanctuary.auth.data.network.api_services.AuthApiService
import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.core.data.network.onError
import com.zg.sanctuary.core.data.network.onSuccess
import com.zg.sanctuary.core.persistence.SanctuaryDatabase
import com.zg.sanctuary.interests.domain.Interest

// Auth repo
class AuthRepository(
    private val authApiService: AuthApiService,
    private val database: SanctuaryDatabase
) {
    suspend fun login(email: String, password: String, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) {
        authApiService.login(
            email = email,
            password = password
        ).onSuccess {
            onSuccess(it)
            // Delete all users from db then save. Only one user can be logged in at any time.
            database.userDao().deleteAllUsers()
            database.userDao().saveUser(it)
        }.onError {
            onFailure(it.error)
        }
    }

    suspend fun createAccount(email: String, password: String, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) {
        authApiService.createAccount(
            email = email,
            password = password
        ).onSuccess {
            onSuccess(it)
            // Delete all users from db then save. Only one user can be logged in at any time.
            database.userDao().deleteAllUsers()
            database.userDao().saveUser(it)
        }.onError {
            onFailure(it.error)
        }
    }

    suspend fun updateUser(
        profileImage: ByteArray,
        fullName: String,
        userName: String,
        dob: String,
        chosenInterests: List<Interest>,
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val loggedInUser: User = database.userDao().getLoggedInUser()
        authApiService.updateUser(
            profileImage = profileImage,
            fullName = fullName,
            userName = userName,
            dob = dob,
            chosenInterests = chosenInterests,
            authToken = loggedInUser.getBearerToken()
        ).onSuccess {
            onSuccess(it)
            // Save user in db
            database.userDao().deleteAllUsers()
            database.userDao().saveUser(it)
        }.onError {
            onFailure(it.error)
        }
    }
}