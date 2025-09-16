package com.example.domain.use_cases

interface ISplashUseCase {
    suspend operator fun invoke(): Boolean
}