package com.example.domain.use_cases

import com.example.domain.repositories.ISplashRepository

class SplashUseCase(private val repository: ISplashRepository) : ISplashUseCase {
    override suspend fun invoke(): Boolean {
        return repository.isUserLoggedIn()
    }
}