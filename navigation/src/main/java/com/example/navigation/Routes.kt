package com.example.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    data object Splash : Routes()
    @Serializable
    data object SignIn : Routes()

    @Serializable
    data object SignUp : Routes()

    @Serializable
    data object Home : Routes()

    @Serializable
    data class Profile(val userId: String) : Routes()

    @Serializable
    data class MovieDetail(val movieId: String) : Routes()
}
