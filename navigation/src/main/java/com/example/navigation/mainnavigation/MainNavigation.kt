package com.example.navigation.mainnavigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.core_ui.base.Routes
import com.example.navigation.components.CustomBottomBar
import com.example.navigation.components.HomeRoute
import com.example.navigation.components.ProfileRoute
import com.example.navigation.components.SearchRoute
import com.example.navigation.components.WatchlistRoute

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    state: BottomNavigationContract.State,
    onEvent: (BottomNavigationContract.Event) -> Unit
) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            CustomBottomBar(
                items = state.items,
                onItemClick = { item ->
                    onEvent(BottomNavigationContract.Event.OnTabSelected(item.route))

                    bottomNavController.navigate(item.route) {
                        popUpTo(bottomNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        NavHost(
            navController = bottomNavController,
            startDestination = Routes.Home
        ) {
            composable<Routes.Home> {
                HomeRoute(
                    navController = navController,
                    bottomNavController = bottomNavController,
                    onBottomNavEvent = onEvent
                )
            }

            composable<Routes.Profile> { backStackEntry ->
                ProfileRoute(
                    navHostController = navController
                )
            }

            composable<Routes.Search> {
                SearchRoute(
                    navController = navController
                )

            }

            composable<Routes.WatchList>{
                WatchlistRoute(
                    navController = navController
                )
            }


        }
    }

}