package com.example.domain.repositories

interface ISplashRepository {
    suspend fun isUserLoggedIn(): Boolean
}