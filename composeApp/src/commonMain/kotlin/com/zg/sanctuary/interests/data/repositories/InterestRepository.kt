package com.zg.sanctuary.interests.data.repositories

import com.zg.sanctuary.core.network.SanctuaryResult
import com.zg.sanctuary.core.network.onError
import com.zg.sanctuary.core.network.onSuccess
import com.zg.sanctuary.core.persistence.SanctuaryDatabase
import com.zg.sanctuary.interests.data.network.api_services.InterestsApiService
import com.zg.sanctuary.interests.data.network.api_services.impls.InterestApiServiceImpl
import com.zg.sanctuary.interests.domain.Interest

class InterestRepository(
    val interestsApiService: InterestsApiService,
    val database : SanctuaryDatabase
) {
    suspend fun getAllInterestsFromNetwork(
        onSuccess: (List<Interest>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        interestsApiService.getAllInterests()
            .onSuccess {
                database.interestDao().insertInterests(it)
                onSuccess(it)
            }
            .onError {
                onFailure(it.error)
            }
    }

    suspend fun getAllInterestsFromDB() : List<Interest>{
        return database.interestDao().getAllInterests()
    }
}