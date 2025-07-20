package com.zg.sanctuary.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.zg.sanctuary.auth.data.network.api_services.AuthApiService
import com.zg.sanctuary.auth.data.network.api_services.impls.AuthApiServiceImpl
import com.zg.sanctuary.auth.data.repositories.AuthRepository
import com.zg.sanctuary.auth.presentation.create_account.CreateAccountViewModel
import com.zg.sanctuary.auth.presentation.login.LoginViewModel
import com.zg.sanctuary.auth.presentation.personal_information.PersonalInformationViewModel
import com.zg.sanctuary.chat.data.network.WebsocketService
import com.zg.sanctuary.chat.data.repositories.ChatRepository
import com.zg.sanctuary.chat.presentation.ChatViewModel
import com.zg.sanctuary.conversations.data.network.ConversationsApiService
import com.zg.sanctuary.conversations.data.network.impls.ConversationsApiServiceImpl
import com.zg.sanctuary.conversations.data.repository.ConversationsRepository
import com.zg.sanctuary.conversations.presentation.ConversationsViewModel
import com.zg.sanctuary.core.persistence.DatabaseFactory
import com.zg.sanctuary.core.persistence.SanctuaryDatabase
import com.zg.sanctuary.friends.data.network.FriendsApiService
import com.zg.sanctuary.friends.data.network.impls.FriendsApiServiceImpl
import com.zg.sanctuary.friends.data.repositories.FriendsRepository
import com.zg.sanctuary.friends.domain.FriendRequest
import com.zg.sanctuary.friends.presentation.FriendsViewModel
import com.zg.sanctuary.home.presentation.HomeViewModel
import com.zg.sanctuary.interests.data.network.api_services.InterestsApiService
import com.zg.sanctuary.interests.data.network.api_services.impls.InterestApiServiceImpl
import com.zg.sanctuary.interests.data.repositories.InterestRepository
import com.zg.sanctuary.posts.data.network.PostApiService
import com.zg.sanctuary.posts.data.network.impls.PostApiServiceImpl
import com.zg.sanctuary.posts.data.repositories.PostRepository
import com.zg.sanctuary.posts.presentation.create_post.CreatePostViewModel
import com.zg.sanctuary.posts.presentation.post_details.PostDetailsViewModel
import com.zg.sanctuary.posts.presentation.post_list.PostListViewModel
import com.zg.sanctuary.profile.data.network.api_services.ProfileApiService
import com.zg.sanctuary.profile.data.network.api_services.impls.ProfileApiServiceImpl
import com.zg.sanctuary.profile.data.repository.ProfileRepository
import com.zg.sanctuary.profile.presentation.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {

    // Application level coroutine scope.
    single<CoroutineScope> {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

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

    // Posts
    single<PostApiService> { PostApiServiceImpl() }
    single<PostRepository> {
        PostRepository(postApiService = get(), database = get())
    }

    // Profile
    single<ProfileApiService> { ProfileApiServiceImpl() }
    single<ProfileRepository> {
        ProfileRepository(profileApiService = get(), database = get())
    }

    // Friend Request
    single<FriendsApiService> { FriendsApiServiceImpl() }
    single<FriendsRepository> {
        FriendsRepository(apiService = get(), database = get())
    }

    // Conversations
    single<ConversationsApiService> { ConversationsApiServiceImpl() }
    single<ConversationsRepository>{
        ConversationsRepository(conversationsApiService = get(), database = get())
    }

    // Chat
    single<WebsocketService> { WebsocketService() }
    single<ChatRepository> {
        ChatRepository(
            websocketService = get(),
            applicationScope = get(),
            database = get()
        )
    }

    // View Models
    viewModelOf(::LoginViewModel)
    viewModelOf(::CreateAccountViewModel)
    viewModelOf(::PersonalInformationViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::PostListViewModel)
    viewModel { params ->
        PostDetailsViewModel(
            postId = params.get(),
            postRepository = get(),
            authRepository = get()
        )
    }
    viewModelOf(::CreatePostViewModel)
    viewModel { params ->
        ProfileViewModel(
            userId = params.get(),
            authRepository = get(),
            postRepository = get(),
            friendsRepository = get(),
            profileRepository = get()
        )
    }
    viewModelOf(::FriendsViewModel)

    viewModel { params ->
        ChatViewModel(
            receiverId = params.get(),
            authRepository = get(),
            chatRepository = get()
        )
    }

    viewModelOf(::ConversationsViewModel)
}