package com.example.data.data_sources

import com.example.user_preferences.IAuthDataSource

class SplashDataSource(private val firebase: IAuthDataSource) : ISplashDataSource {
    override suspend fun isUserLoggedIn(): Boolean {
        return firebase.isUserAuthenticated()
    }
}