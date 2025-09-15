package com.example.data.data_sources

import com.example.domain.models.AuthResult
import com.example.domain.models.SignInRequest
import com.example.domain.models.SignUpRequest
import com.example.domain.models.User

interface IAuthDataSource {
    suspend fun signUp(request: SignUpRequest): AuthResult<User>
    suspend fun signIn(request: SignInRequest): AuthResult<User>
    suspend fun signOut()
    suspend fun getCurrentUser(): User?
    suspend fun checkUsernameAvailability(username: String): AuthResult<Boolean>
}