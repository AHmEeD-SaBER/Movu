package com.example.domain.use_cases.login

import com.example.domain.models.DomainAuthResult
import com.example.domain.models.DomainSignInRequest
import com.example.domain.models.DomainUser
import com.example.domain.repositories.IAuthRepository

class SignInUseCase(private val repository: IAuthRepository) : ISignInUseCase {
    override suspend fun invoke(request: DomainSignInRequest): DomainAuthResult<DomainUser> {
        return repository.signIn(request)
    }

}