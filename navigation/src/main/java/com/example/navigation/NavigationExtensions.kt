package com.example.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.example.core_domain.MediaType
import com.example.core_ui.base.Routes

// Extension functions for easier navigation using built-in NavController

fun NavController.navigateToSignIn() {
    navigate(Routes.SignIn)
}

fun NavController.navigateToSignUp() {
    navigate(Routes.SignUp)
}

fun NavController.navigateToMain() {
    navigate(Routes.Main) {
        popUpTo(0) { inclusive = true }
    }
}


fun NavController.navigateToMovieDetail(mediaId: Int, mediaType: MediaType) {
    navigate(Routes.Details(mediaId, mediaType))
}

fun NavController.navigateToSearch() {
    navigate(Routes.Search)
}

fun NavController.logout(){
    navigate(Routes.SignIn) {
        popUpTo(0) { inclusive = true }
    }
}

// Navigation from Auth to SignIn/SignUp without clearing back stack
fun NavController.navigateToSignInFromAuth() {
    navigate(Routes.SignIn) {
        popUpTo(Routes.SignIn) { inclusive = false }
    }
}
