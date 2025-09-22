package com.example.navigation.components

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.navigation.navigateToMovieDetail
import com.example.ui.home_screen.HomeViewModel
import com.example.ui.home_screen.screen.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(
    navController: NavHostController
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
                is com.example.ui.home_screen.HomeContract.Effects.NavigateToDetail -> {
                    navController.navigateToMovieDetail(
                        mediaId = effect.mediaItemId,
                        mediaType = effect.mediaType
                    )
                }
            }
        }
    }
}
