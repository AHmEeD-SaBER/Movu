package com.example.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

// Extension functions for easier navigation using built-in NavController
fun NavController.navigateToSplash() {
    navigate(Routes.Splash)
}

fun NavController.navigateToSignIn() {
    navigate(Routes.SignIn)
}

fun NavController.navigateToSignUp() {
    navigate(Routes.SignUp)
}

fun NavController.navigateToHome() {
    navigate(Routes.Home) {
        popUpTo(0) { inclusive = true }
    }
}

fun NavController.navigateToProfile(userId: String) {
    navigate(Routes.Profile(userId))
}

fun NavController.navigateToMovieDetail(movieId: String) {
    navigate(Routes.MovieDetail(movieId))
}

// Navigation from Auth to SignIn/SignUp without clearing back stack
fun NavController.navigateToSignInFromAuth() {
    navigate(Routes.SignIn) {
        popUpTo(Routes.SignIn) { inclusive = false }
    }
}

fun NavController.navigateToSignUpFromAuth() {
    navigate(Routes.SignUp) {
        popUpTo(Routes.SignUp) { inclusive = false }
    }
}

// Navigation between SignIn and SignUp
fun NavController.navigateFromSignInToSignUp() {
    navigate(Routes.SignUp) {
        popUpTo(Routes.SignIn) { inclusive = true }
    }
}

fun NavController.navigateFromSignUpToSignIn() {
    navigate(Routes.SignIn) {
        popUpTo(Routes.SignUp) { inclusive = true }
    }
}
