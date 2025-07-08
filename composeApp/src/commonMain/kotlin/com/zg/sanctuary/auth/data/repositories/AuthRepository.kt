package com.zg.sanctuary.auth.data.repositories

import com.zg.sanctuary.auth.data.network.api_services.AuthApiService
import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.profile.domain.UserProfileDetails
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
            email = email.trim(),
            password = password.trim()
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
        val loggedInUser: User? = database.userDao().getLoggedInUser()
        loggedInUser?.let {
            authApiService.updateUser(
                profileImage = profileImage,
                fullName = fullName,
                userName = userName,
                dob = dob,
                chosenInterests = chosenInterests,
                authToken = it.getBearerToken()
            ).onSuccess {
                onSuccess(it)
                // Save user in db
                database.userDao().deleteAllUsers()
                database.userDao().saveUser(it)
            }.onError {
                onFailure(it.error)
            }
        } ?: run {
            onFailure("User is not logged in. Please close the app, re-open the app and try again.")
        }
    }

    suspend fun getLoggedInUser(): User? {
        return database.userDao().getLoggedInUser()
    }

    suspend fun isUserLoggedIn(): Boolean {
        val loggedInUser = database.userDao().getLoggedInUser()
        return loggedInUser != null
    }

}