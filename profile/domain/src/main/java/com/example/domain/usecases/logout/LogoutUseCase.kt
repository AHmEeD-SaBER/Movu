package com.example.domain.usecases.logout

import com.example.domain.models.UserResult
import com.example.domain.repositories.IUserRepository
import kotlinx.coroutines.flow.Flow

class LogoutUseCase(
    private val repository: IUserRepository
) : ILogoutUseCase {
    override suspend fun invoke(): Flow<UserResult<Unit>> {
        return repository.logout()
    }
}
