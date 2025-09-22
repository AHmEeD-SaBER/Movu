package com.example.navigation.components

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.core_domain.MediaType
import com.example.ui.DetailsViewModel
import com.example.ui.screen.DetailsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsRoute(
    mediaId: Int,
    mediaType: MediaType,
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: DetailsViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()

    DetailsScreen(
        state = state,
        onEvent = viewModel::handleEvent,
        mediaType = mediaType
    )
    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is com.example.ui.DetailsContract.Effects.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

}
