package com.example.navigation.components

import android.content.*
import android.content.Intent
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
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = effect.trailerLink.toUri()
                    }
                    context.startActivity(intent)
                }

                is DetailsContract.Effects.ShowWatchlistError -> {
                    // Handle watchlist error - could show a snackbar or toast
                    // For now, we'll just log the error
                    android.util.Log.e("DetailsRoute", "Watchlist error: ")
                }

                DetailsContract.Effects.ReviewSubmittedSuccessfully -> {
                    // Handle successful review submission
                    // Could show a success message or refresh the data
                    android.util.Log.d("DetailsRoute", "Review submitted successfully")
                }

                is DetailsContract.Effects.ShowReviewError -> {
                    // Handle review submission error
                    android.util.Log.e("DetailsRoute", "Review error:")
                }
            }
        }
    }

}
