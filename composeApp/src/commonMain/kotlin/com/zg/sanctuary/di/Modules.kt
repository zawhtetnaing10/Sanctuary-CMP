package com.zg.sanctuary.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.zg.sanctuary.auth.presentation.login.LoginViewModel
import com.zg.sanctuary.core.persistence.DatabaseFactory
import com.zg.sanctuary.core.persistence.SanctuaryDatabase
import com.zg.sanctuary.interests.data.network.api_services.InterestsApiService
import com.zg.sanctuary.interests.data.network.api_services.impls.InterestApiServiceImpl
import com.zg.sanctuary.interests.data.repositories.InterestRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {

    // Database
    single<SanctuaryDatabase> {
        get<DatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    // Interest
    single<InterestsApiService> { InterestApiServiceImpl() }
    single<InterestRepository> {
        InterestRepository(interestsApiService = get(), database = get())
    }

    // View Models
    viewModelOf(::LoginViewModel)
}