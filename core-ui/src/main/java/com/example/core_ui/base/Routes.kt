package com.example.core_ui.base

import com.example.core_domain.MediaType
import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
sealed class Routes {
    @kotlinx.serialization.Serializable
    data object Splash : Routes()
    @kotlinx.serialization.Serializable
    data object SignIn : Routes()

    @kotlinx.serialization.Serializable
    data object SignUp : Routes()

    @kotlinx.serialization.Serializable
    data object Home : Routes()

    @kotlinx.serialization.Serializable
    data class Profile(val userId: String) : Routes()

    @Serializable
    data class Details(val mediaId: Int, val mediaType: MediaType) : Routes()
}