package com.example.domain.use_cases.get_count

import com.example.domain.repositories.IUserRepository
import kotlinx.coroutines.flow.flow

class GetUserUseCase(private val repository: IUserRepository) : IGetUserUseCase {
    override suspend fun invoke() = repository.getCurrentUser()
}
