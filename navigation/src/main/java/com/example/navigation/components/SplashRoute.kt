package com.example.navigation.components

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.navigation.navigateToHome
import com.example.navigation.navigateToSignIn
import com.example.ui.SplashContract
import com.example.ui.SplashViewModel
import com.example.ui.screen.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashRoute(
    navController: NavHostController
) {
    val splashViewModel: SplashViewModel = koinViewModel()
    val state by splashViewModel.uiState.collectAsState()

    SplashScreen(
        state = state,
        onEvent = splashViewModel::handleEvent,
        modifier = Modifier
    )

    LaunchedEffect(splashViewModel) {
        splashViewModel.effect.collect { effect ->
            when (effect) {
                is SplashContract.Effect.NavigateToAuth -> {
                    navController.navigateToSignIn()
                }
                is SplashContract.Effect.NavigateToHome -> {
                    navController.navigateToHome()
                }
            }
        }
    }
}
