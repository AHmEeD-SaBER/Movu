package com.example.domain.use_cases.signup

import com.example.domain.models.DomainAuthResult
import com.example.domain.models.DomainSignUpRequest
import com.example.domain.models.DomainUser
import com.example.domain.repositories.IAuthRepository

class SignUpUseCase(private val repository: IAuthRepository) : ISignUpUseCase {
    override suspend fun invoke(request: DomainSignUpRequest): DomainAuthResult<DomainUser> {
        return repository.signUp(request)
    }

}