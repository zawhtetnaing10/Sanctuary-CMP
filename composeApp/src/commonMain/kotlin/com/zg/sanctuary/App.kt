package com.zg.sanctuary

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.zg.sanctuary.auth.presentation.create_account.CreateAccountRoute
import com.zg.sanctuary.auth.presentation.create_account.CreateAccountViewModel
import com.zg.sanctuary.auth.presentation.login.LoginRoute
import com.zg.sanctuary.auth.presentation.login.LoginViewModel
import com.zg.sanctuary.auth.presentation.personal_information.PersonalInformationRoute
import com.zg.sanctuary.auth.presentation.personal_information.PersonalInformationViewModel
import com.zg.sanctuary.core.BeVietnamProTypography
import com.zg.sanctuary.home.presentation.HomeRoute
import com.zg.sanctuary.posts.presentation.create_post.CreatePostRoute
import com.zg.sanctuary.posts.presentation.post_details.PostDetailsRoute
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme(
        typography = BeVietnamProTypography()
    ) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = AppRoute.Home //TODO:- Change back after testing
        ) {
            // Auth
            navigation<AppRoute.AuthGraph>(startDestination = AppRoute.Login) { // TODO - Change back to AppRoute.Login after testing
                composable<AppRoute.Login>(
                    enterTransition = { enterTransition },
                    popEnterTransition = { popEnterTransition },
                    popExitTransition = { popExitTransition }
                ) {
                    val viewModel = koinViewModel<LoginViewModel>()
                    LoginRoute(
                        viewModel = viewModel,
                        onNavToHomeTriggered = {

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
                        onNavigateToHomeTriggered = {},
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
                HomeRoute(onNavigateToPostDetails = { postId ->
                    navController.navigate(AppRoute.PostDetails(postId))
                }, onNavigateToCreatePost = {
                    navController.navigate(AppRoute.CreatePost)
                })
            }

            // Post Details
            composable<AppRoute.PostDetails>(
                enterTransition = { enterTransition },
                popEnterTransition = { popEnterTransition },
                popExitTransition = { popExitTransition }
            ) { backStackEntry ->

                val args = backStackEntry.toRoute<AppRoute.PostDetails>()
                val postId = args.postId

                // TODO: - Set up view model here

                PostDetailsRoute()
            }

            // Create Post
            composable<AppRoute.CreatePost>(
                enterTransition = { enterTransition },
                popEnterTransition = { popEnterTransition },
                popExitTransition = { popExitTransition }
            ) {
                CreatePostRoute()
            }
        }
    }
}

// Transitions for navigation
val enterTransition = slideInHorizontally(initialOffsetX = { it / 2 })
val popEnterTransition = slideInHorizontally(initialOffsetX = { -it / 4 })
val popExitTransition = slideOutHorizontally(targetOffsetX = { it })

