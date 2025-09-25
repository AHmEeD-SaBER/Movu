package com.example.navigation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.navigation.mainnavigation.BottomNavigationViewModel
import com.example.navigation.mainnavigation.MainNavigation
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainRoute(modifier: Modifier = Modifier, navController: NavHostController) {

    val bottomNavigationViewModel: BottomNavigationViewModel = koinViewModel()
    val state by bottomNavigationViewModel.uiState.collectAsState()

    MainNavigation(
        modifier = modifier,
        navController = navController,
        state = state,
        onEvent = bottomNavigationViewModel::handleEvent
    )

}