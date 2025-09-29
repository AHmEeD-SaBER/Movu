package com.example.navigation.components

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.core_ui.base.Routes
import com.example.navigation.mainnavigation.BottomNavigationContract
import com.example.navigation.navigateToMovieDetail
import com.example.ui.home_screen.HomeViewModel
import com.example.ui.home_screen.screen.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(
    navController: NavHostController,
    bottomNavController: NavHostController,
    onBottomNavEvent: (BottomNavigationContract.Event) -> Unit
) {
    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()
    HomeScreen(
        state = state,
        onEvent = viewModel::handleEvent,
        modifier = Modifier
    )

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is com.example.ui.home_screen.HomeContract.Effects.NavigateToDetails -> {
                    navController.navigateToMovieDetail(
                        mediaId = effect.mediaItemId,
                        mediaType = effect.mediaType
                    )
                }
                is com.example.ui.home_screen.HomeContract.Effects.NavigateToSearch -> {
                    // Update bottom navigation selection to Search tab
                    onBottomNavEvent(BottomNavigationContract.Event.OnTabSelected(Routes.Search))

                    // Navigate to search using bottom nav controller
                    bottomNavController.navigate(Routes.Search) {
                        popUpTo(bottomNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }
}
