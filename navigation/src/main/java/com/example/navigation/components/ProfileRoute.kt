package com.example.navigation.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.navigation.logout
import com.example.ui.ProfileContract
import com.example.ui.ProfileViewModel
import com.example.ui.screen.ProfileScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileRoute(
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = koinViewModel()
    val state = viewModel.uiState.collectAsState()

    ProfileScreen(
        state = state.value,
        onEvent = viewModel::handleEvent,
    )

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when(effect){
                ProfileContract.Effects.NavigateToAuth -> {
                    navHostController.logout()
                }
                is ProfileContract.Effects.ShowError -> {
                    val errorTitle = context.getString(effect.error.titleRes)
                    val errorSubtitle = context.getString(effect.error.subtitleRes)
                    val errorMessage = "$errorTitle: $errorSubtitle"

                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
