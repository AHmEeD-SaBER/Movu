package com.example.navigation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.core_ui.R
import com.example.navigation.navigateToMovieDetail
import com.example.ui.WatchlistContract
import com.example.ui.WatchlistViewModel
import com.example.ui.screen.WatchlistScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun WatchlistRoute(modifier: Modifier = Modifier, navController : NavHostController) {

    val viewModel : WatchlistViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    WatchlistScreen(
        modifier = modifier,
        state = state,
        onEvent = viewModel::handleEvent

    )

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is WatchlistContract.Effects.NavigateToDetails -> {
                    navController.navigateToMovieDetail(
                        mediaId = effect.mediaItemId,
                        mediaType = effect.mediaType
                    )
                }

                is WatchlistContract.Effects.ShowDeleteError -> {

                }

                WatchlistContract.Effects.ShowDeleteSuccess -> {
                    android.widget.Toast.makeText(
                        context,
                        context.getString(R.string.delete_success_message),
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}