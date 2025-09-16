package com.example.data.repositories

import com.example.core_data.utils.INetworkMonitor
import com.example.data.data_sources.ISplashDataSource
import com.example.domain.repositories.ISplashRepository

class SplashRepository(
    private val dataSource: ISplashDataSource,
    private val networkMonitor: INetworkMonitor
) : ISplashRepository {
    override suspend fun isUserLoggedIn(): Boolean {
        val isConnected = networkMonitor.isNetworkAvailable()
        return if (isConnected) {
            dataSource.isUserLoggedIn()
        } else {
            false
        }
    }
}