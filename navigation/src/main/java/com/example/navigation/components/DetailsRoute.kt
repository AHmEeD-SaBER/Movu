package com.example.navigation.components

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.core_domain.MediaType
import com.example.ui.DetailsContract
import com.example.ui.DetailsViewModel
import com.example.ui.screen.DetailsScreen
import org.koin.androidx.compose.koinViewModel
import androidx.core.net.toUri

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
                is DetailsContract.Effects.NavigateBack -> {
                    navController.popBackStack()
                }

                is DetailsContract.Effects.OpenTrailer -> {
                    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
                        data = effect.trailerLink.toUri()
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

}
