package com.zg.sanctuary

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.zg.sanctuary.auth.presentation.create_account.CreateAccountRoute
import com.zg.sanctuary.auth.presentation.login.LoginRoute
import com.zg.sanctuary.auth.presentation.personal_information.PersonalInformationScreen
import com.zg.sanctuary.core.BeVietnamProTypography
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(
        typography = BeVietnamProTypography()
    ) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = AppRoute.AuthGraph
        ) {
            navigation<AppRoute.AuthGraph>(startDestination = AppRoute.Login) {
                composable<AppRoute.Login>(
                    enterTransition = { slideInHorizontally(initialOffsetX = { it / 2 }) },
                    popEnterTransition = { slideInHorizontally(initialOffsetX = { -it / 4 }) },
                    popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
                ) {
                    LoginRoute(
                        onLoginClicked = {},
                        onSignUpClicked = {
                            navController.navigate(AppRoute.CreateAccount)
                        }
                    )
                }
                composable<AppRoute.CreateAccount>(
                    enterTransition = { slideInHorizontally(initialOffsetX = { it / 2 }) },
                    popEnterTransition = { slideInHorizontally(initialOffsetX = { -it / 4 }) },
                    popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
                ) {
                    CreateAccountRoute(
                        onClickBack = {
                            navController.navigateUp()
                        },
                        onClickSignUp = {
                            navController.navigate(AppRoute.PersonalInformation)
                        }
                    )
                }
                composable<AppRoute.PersonalInformation>(
                    enterTransition = { slideInHorizontally(initialOffsetX = { it / 2 }) },
                    popEnterTransition = { slideInHorizontally(initialOffsetX = { -it / 4 }) },
                    popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
                ) {
                    PersonalInformationScreen(
                        onClickNext = {},
                        onTapBack = {
                            navController.navigateUp()
                        }
                    )
                }
            }

        }
    }
}

// Transitions for navigation
//val slideInTransitionOffset: (Int) -> Int = { it / 2 }
//val sanctuaryEnterTransition: EnterTransition = slideInHorizontally(initialOffsetX = slideInTransitionOffset)

