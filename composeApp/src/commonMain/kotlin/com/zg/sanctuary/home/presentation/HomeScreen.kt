package com.zg.sanctuary.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.zg.sanctuary.AppRoute
import com.zg.sanctuary.auth.domain.User
import com.zg.sanctuary.core.MARGIN_40
import com.zg.sanctuary.core.MARGIN_LARGE
import com.zg.sanctuary.core.MARGIN_XSMALL
import com.zg.sanctuary.core.PRIMARY_COLOR
import com.zg.sanctuary.friends.presentation.FriendsRoute
import com.zg.sanctuary.posts.presentation.post_list.PostListRoute
import com.zg.sanctuary.posts.presentation.post_list.PostListViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import sanctuary.composeapp.generated.resources.Res
import sanctuary.composeapp.generated.resources.friends_selected
import sanctuary.composeapp.generated.resources.friends_unselected
import sanctuary.composeapp.generated.resources.home_selected
import sanctuary.composeapp.generated.resources.home_unselected
import sanctuary.composeapp.generated.resources.image_loading_error
import sanctuary.composeapp.generated.resources.loading_skeleton
import sanctuary.composeapp.generated.resources.sample_profile_picture
import kotlin.math.log

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    onNavigateToPostDetails: (Int) -> Unit,
    onNavigateToCreatePost: () -> Unit,
    onNavigateToProfile: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onNavigateToPostDetails = onNavigateToPostDetails,
        onNavigateToCreatePost = onNavigateToCreatePost,
        onNavigateToProfile = onNavigateToProfile
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onNavigateToPostDetails: (Int) -> Unit,
    onNavigateToCreatePost: () -> Unit,
    onNavigateToProfile: (Int) -> Unit,
) {

    val homeNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            HomeBottomNavigationBar(
                loggedInUser = state.loggedInUser,
                navController = homeNavController,
                onNavigateToProfile = {
                    state.loggedInUser?.let {
                        onNavigateToProfile(state.loggedInUser.id)
                    }
                }
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
                    val postListViewModel = koinViewModel<PostListViewModel>()
                    PostListRoute(
                        postListViewModel,
                        onNavigateToCreatePost = {
                            onNavigateToCreatePost()
                        },
                        onNavigateToPostDetails = { postId ->
                            onNavigateToPostDetails(postId)
                        },
                        onNavigateToProfile = { userId ->
                            onNavigateToProfile(userId)
                        }
                    )
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
        }
    }
}

@Composable
fun HomeBottomNavigationBar(
    loggedInUser: User?,
    navController: NavHostController,
    onNavigateToProfile: () -> Unit,
) {
    // Backstack entry and current destination from NavController
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Bottom Navigation Items. With their root graphs
    val bottomNavItemsWithRootGraphs = listOf<BottomNavItem>(
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
        ),
    )

    BottomAppBar(modifier = Modifier.fillMaxWidth(), containerColor = Color.White) {
        // Bottom nav items with root graph
        bottomNavItemsWithRootGraphs.forEach { navItem ->
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

        // For logged in user profile
        if(loggedInUser != null)
            NavigationBarItem(
                icon = {
                    AsyncImage(
                        model= loggedInUser.profileImageUrl,
                        contentDescription = "User profile image",
                        placeholder = painterResource(Res.drawable.loading_skeleton),
                        error = painterResource(Res.drawable.image_loading_error),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(MARGIN_40)
                            .clip(CircleShape)
                            .border(
                                width = MARGIN_XSMALL,
                                color = PRIMARY_COLOR,
                                shape = CircleShape
                            )
                            .clickable {
                                onNavigateToProfile()
                            }
                    )
                },
                onClick = {
                    onNavigateToProfile()
                },
                selected = false
            )
    }
}

// Data class for bottom nav
data class BottomNavItem(
    val selectedIcon: Painter,
    val unSelectedIcon: Painter,
    val rootGraph: AppRoute,
    val routeToCheckSelected: AppRoute
)

