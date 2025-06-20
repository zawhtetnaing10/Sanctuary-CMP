package com.zg.sanctuary.di

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.zg.sanctuary.auth.data.network.api_services.AuthApiService
import com.zg.sanctuary.auth.data.network.api_services.impls.AuthApiServiceImpl
import com.zg.sanctuary.auth.data.repositories.AuthRepository
import com.zg.sanctuary.auth.presentation.create_account.CreateAccountViewModel
import com.zg.sanctuary.auth.presentation.login.LoginViewModel
import com.zg.sanctuary.auth.presentation.personal_information.PersonalInformationViewModel
import com.zg.sanctuary.core.persistence.DatabaseFactory
import com.zg.sanctuary.core.persistence.SanctuaryDatabase
import com.zg.sanctuary.interests.data.network.api_services.InterestsApiService
import com.zg.sanctuary.interests.data.network.api_services.impls.InterestApiServiceImpl
import com.zg.sanctuary.interests.data.repositories.InterestRepository
import com.zg.sanctuary.posts.presentation.post_list.PostListViewModel
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

    // Auth
    single<AuthApiService> { AuthApiServiceImpl() }
    single<AuthRepository> {
        AuthRepository(authApiService = get(), database = get())
    }

    // PostList

    // View Models
    viewModelOf(::LoginViewModel)
    viewModelOf(::CreateAccountViewModel)
    viewModelOf(::PersonalInformationViewModel)
    viewModelOf(::PostListViewModel)
}