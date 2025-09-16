package com.example.navigation.components

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.navigation.Routes
import com.example.navigation.navigateToHome
import com.example.navigation.navigateToSignUp
import com.example.ui.signin.SignInContract
import com.example.ui.signin.SignInViewModel
import com.example.ui.signin.screen.SignInScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInRoute(
    navController: NavHostController
) {
    val signInViewModel: SignInViewModel = koinViewModel()
    val state by signInViewModel.uiState.collectAsState()
    val context = LocalContext.current

    SignInScreen(
        state = state,
        onEvent = signInViewModel::handleEvent,
        modifier = Modifier
    )

    LaunchedEffect(signInViewModel) {
        signInViewModel.effect.collect { effect ->
            when (effect) {
                is SignInContract.Effect.NavigateToSignUp -> {
                    navController.navigateToSignUp()
                }
                is SignInContract.Effect.NavigateToHome -> {
                    navController.navigate(Routes.Home) {
                        popUpTo<Routes.SignIn> { inclusive = true }
                    }
                }
                is SignInContract.Effect.ShowError -> {
                    Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                }
                is SignInContract.Effect.ShowSuccess -> {
                    Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
