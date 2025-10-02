package com.example.domain.usecases.logout

import com.example.domain.models.UserResult
import kotlinx.coroutines.flow.Flow

interface ILogoutUseCase {
    suspend operator fun invoke(): Flow<UserResult<Unit>>
}
