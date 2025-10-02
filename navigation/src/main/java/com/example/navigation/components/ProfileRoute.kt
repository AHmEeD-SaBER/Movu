package com.example.navigation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.navigation.logout
import com.example.navigation.navigateToSignIn
import com.example.ui.ProfileContract
import com.example.ui.ProfileViewModel
import com.example.ui.screen.ProfileScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileRoute(
    navHostController: NavHostController
) {
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
                is ProfileContract.Effects.ShowError -> TODO()
            }
        }
    }
}




