package com.example.navigation.components

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.navigation.navigateToMain
import com.example.navigation.navigateToSignIn
import com.example.ui.signup.SignUpContract
import com.example.ui.signup.SignUpViewModel
import com.example.ui.signup.screen.SignUpScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpRoute(
    navController: NavHostController
) {
    val signUpViewModel: SignUpViewModel = koinViewModel()
    val state by signUpViewModel.uiState.collectAsState()
    val context = LocalContext.current

    SignUpScreen(
        state = state,
        onEvent = signUpViewModel::handleEvent,
        modifier = Modifier
    )

    LaunchedEffect(signUpViewModel) {
        signUpViewModel.effect.collect { effect ->
            when (effect) {
                is SignUpContract.Effect.NavigateToSignIn -> {
                    navController.navigateToSignIn()
                }
                is SignUpContract.Effect.NavigateToHome -> {
                    navController.navigateToMain()
                }
                is SignUpContract.Effect.ShowError -> {
                    Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                }
                is SignUpContract.Effect.ShowSuccess -> {
                    Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
