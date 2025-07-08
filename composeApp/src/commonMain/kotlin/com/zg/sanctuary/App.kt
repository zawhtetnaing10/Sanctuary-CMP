package com.zg.sanctuary

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.zg.sanctuary.auth.data.repositories.AuthRepository
import com.zg.sanctuary.auth.presentation.create_account.CreateAccountRoute
import com.zg.sanctuary.auth.presentation.create_account.CreateAccountViewModel
import com.zg.sanctuary.auth.presentation.login.LoginRoute
import com.zg.sanctuary.auth.presentation.login.LoginViewModel
import com.zg.sanctuary.auth.presentation.personal_information.PersonalInformationRoute
import com.zg.sanctuary.auth.presentation.personal_information.PersonalInformationViewModel
import com.zg.sanctuary.core.BeVietnamProTypography
import com.zg.sanctuary.home.presentation.HomeRoute
import com.zg.sanctuary.home.presentation.HomeViewModel
import com.zg.sanctuary.posts.presentation.create_post.CreatePostRoute
import com.zg.sanctuary.posts.presentation.create_post.CreatePostViewModel
import com.zg.sanctuary.posts.presentation.post_details.PostDetailsRoute
import com.zg.sanctuary.posts.presentation.post_details.PostDetailsViewModel
import com.zg.sanctuary.profile.presentation.ProfileRoute
import com.zg.sanctuary.splash.presentation.SplashScreen
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App() {

    val authRepo: AuthRepository = koinInject<AuthRepository>()
    var startDestination: AppRoute by remember { mutableStateOf(AppRoute.Splash) }

    LaunchedEffect(Unit) {
        // Show splash screen for 3 seconds.
        delay(timeMillis = 2000)
        startDestination = if (authRepo.isUserLoggedIn()) AppRoute.Home else AppRoute.AuthGraph
    }

    MaterialTheme(
        typography = BeVietnamProTypography()
    ) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {

            // Splash
            composable<AppRoute.Splash> {
                SplashScreen()
            }

            // Auth
            navigation<AppRoute.AuthGraph>(startDestination = AppRoute.Login) {
                composable<AppRoute.Login>(
                    enterTransition = { enterTransition },
                    popEnterTransition = { popEnterTransition },
                    popExitTransition = { popExitTransition }
                ) {
                    val viewModel = koinViewModel<LoginViewModel>()
                    LoginRoute(
                        viewModel = viewModel,
                        onNavToHomeTriggered = {
                            navController.navigate(AppRoute.Home)
                        },
                        onNavigateToSignUpTriggered = {
                            navController.navigate(AppRoute.CreateAccount)
                        }
                    )
                }
                composable<AppRoute.CreateAccount>(
                    enterTransition = { enterTransition },
                    popEnterTransition = { popEnterTransition },
                    popExitTransition = { popExitTransition }
                ) {
                    val createAccountViewModel = koinViewModel<CreateAccountViewModel>()
                    CreateAccountRoute(
                        viewModel = createAccountViewModel,
                        onNavigateBackTriggered = {
                            navController.navigateUp()
                        },
                        onNavigateToPersonalInfoTriggered = {
                            navController.navigate(AppRoute.PersonalInformation)
                        }
                    )
                }
                composable<AppRoute.PersonalInformation>(
                    enterTransition = { enterTransition },
                    popEnterTransition = { popEnterTransition },
                    popExitTransition = { popExitTransition }
                ) {
                    val personalInfoVM = koinViewModel<PersonalInformationViewModel>()
                    PersonalInformationRoute(
                        viewModel = personalInfoVM,
                        onNavigateToHomeTriggered = {
                            navController.navigate(AppRoute.Home)
                        },
                        onBackTriggered = {
                            navController.navigateUp()
                        }
                    )
                }
            }

            // Home
            composable<AppRoute.Home>(
                enterTransition = { enterTransition },
                popEnterTransition = { popEnterTransition },
                popExitTransition = { popExitTransition }
            ) {

                val homeViewModel = koinViewModel<HomeViewModel>()

                HomeRoute(
                    viewModel = homeViewModel,
                    onNavigateToPostDetails = { postId ->
                        navController.navigate(AppRoute.PostDetails(postId))
                    },
                    onNavigateToCreatePost = {
                        navController.navigate(AppRoute.CreatePost)
                    },
                    onNavigateToProfile = { userId ->
                        navController.navigate(AppRoute.ProfileDetails(userId))
                    },
                )
            }

            // Post Details
            composable<AppRoute.PostDetails>(
                enterTransition = { enterTransition },
                popEnterTransition = { popEnterTransition },
                popExitTransition = { popExitTransition }
            ) { backStackEntry ->

                val args = backStackEntry.toRoute<AppRoute.PostDetails>()
                val postId = args.postId

                // View Model
                val postDetailsViewModel = koinViewModel<PostDetailsViewModel>(parameters = { parametersOf(postId) })

                PostDetailsRoute(
                    viewModel = postDetailsViewModel,
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    onNavigateToProfile = { userId ->
                        navController.navigate(AppRoute.ProfileDetails(userId))
                    }
                )
            }

            // Create Post
            composable<AppRoute.CreatePost>(
                enterTransition = { enterTransition },
                popEnterTransition = { popEnterTransition },
                popExitTransition = { popExitTransition }
            ) {
                val viewModel = koinViewModel<CreatePostViewModel>()

                CreatePostRoute(
                    viewModel = viewModel,
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }


            composable<AppRoute.ProfileDetails>(
                enterTransition = { enterTransition },
                popEnterTransition = { popEnterTransition },
                popExitTransition = { popExitTransition }
            ) { backStackEntry ->

                val args = backStackEntry.toRoute<AppRoute.ProfileDetails>()
                val userId = args.userId

                ProfileRoute()
            }
        }
    }
}

// Transitions for navigation
val enterTransition = slideInHorizontally(initialOffsetX = { it / 2 })
val popEnterTransition = slideInHorizontally(initialOffsetX = { -it / 4 })
val popExitTransition = slideOutHorizontally(targetOffsetX = { it })

