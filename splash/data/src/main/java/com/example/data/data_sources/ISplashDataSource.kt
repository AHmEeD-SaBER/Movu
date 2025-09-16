package com.example.data.data_sources

interface ISplashDataSource {
    suspend fun isUserLoggedIn(): Boolean
}