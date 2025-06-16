package com.zg.sanctuary.home.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.zg.sanctuary.AppRoute
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.friends.presentation.FriendsRoute
import com.zg.sanctuary.posts.presentation.PostListRoute
import com.zg.sanctuary.profile.presentation.ProfileRoute
import org.jetbrains.compose.resources.painterResource
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.friends_selected
import sanctuary.composeapp.generated.resources.friends_unselected
import sanctuary.composeapp.generated.resources.home_selected
import sanctuary.composeapp.generated.resources.home_unselected

@Composable
fun HomeRoute(
) {
    HomeScreen()
}

@Composable
fun HomeScreen() {

    val homeNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            HomeBottomNavigationBar(
                navController = homeNavController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = homeNavController,
            startDestination = AppRoute.PostsGraph
        ) {
            // Post graph
            navigation<AppRoute.PostsGraph>(
                startDestination = AppRoute.PostList
            ) {
                composable<AppRoute.PostList> {
                    PostListRoute()
                }
            }

            // Friends Graph
            navigation<AppRoute.FriendsGraph>(
                startDestination = AppRoute.Friends
            ) {
                composable<AppRoute.Friends> {
                    FriendsRoute()
                }
            }

            // Profile Graph
            navigation<AppRoute.ProfileGraph>(
                startDestination = AppRoute.Profile
            ) {
                composable<AppRoute.Profile> {
                    ProfileRoute()
                }
            }
        }
    }
}

@Composable
fun HomeBottomNavigationBar(
    navController: NavHostController
) {
    // Backstack entry and current destination from NavController
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Bottom Navigation Items
    val bottomNavItems = listOf<BottomNavItem>(
        BottomNavItem(
            selectedIcon = painterResource(Res.drawable.home_selected),
            unSelectedIcon = painterResource(Res.drawable.home_unselected),
            routeToCheckSelected = AppRoute.PostList,
            rootGraph = AppRoute.PostsGraph
        ),
        BottomNavItem(
            selectedIcon = painterResource(Res.drawable.friends_selected),
            unSelectedIcon = painterResource(Res.drawable.friends_unselected),
            routeToCheckSelected = AppRoute.Friends,
            rootGraph = AppRoute.FriendsGraph
        )
    )

    BottomAppBar(modifier = Modifier.fillMaxWidth(), containerColor = Color.White) {
        bottomNavItems.forEach { navItem ->
            val selected = currentDestination?.hierarchy?.any { it.hasRoute(navItem.routeToCheckSelected::class) } == true
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = if (selected) navItem.selectedIcon else navItem.unSelectedIcon,
                        contentDescription = null,
                        modifier = Modifier.size(MARGIN_LARGE)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    indicatorColor = Color.Transparent
                ),
                onClick = {
                    navController.navigate(navItem.rootGraph) {
                        popUpTo(navController.graph.findStartDestination().route ?: "") {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                selected = selected
            )
        }
    }
}

// Data class for bottom nav
data class BottomNavItem(
    val selectedIcon: Painter,
    val unSelectedIcon: Painter,
    val rootGraph: AppRoute,
    val routeToCheckSelected: AppRoute
)

