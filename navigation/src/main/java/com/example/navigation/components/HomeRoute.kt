package com.example.navigation.components

import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.navigation.Routes
import com.example.ui.home_screen.HomeViewModel
import com.example.ui.home_screen.screen.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(
    navController: NavHostController
) {
    val context = LocalContext.current
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
                    Toast.makeText(
                        context,
                        "Navigate to detail of ${effect.mediaItemId} with ID ${effect.mediaItemId}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
