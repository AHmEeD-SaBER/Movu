package com.example.navigation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.navigation.navigateToMovieDetail
import com.example.ui.screen.SearchScreen
import com.example.ui.screen.SearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchRoute(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: SearchViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()
    SearchScreen(
        state = state,
        onEvent = viewModel::handleEvent,
        modifier = modifier
    )

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is com.example.ui.screen.SearchContract.Effects.NavigateToDetails -> {
                    navController.navigateToMovieDetail(
                        mediaId = effect.mediaItemId,
                        mediaType = effect.mediaType
                    )
                }
            }
        }
    }



}