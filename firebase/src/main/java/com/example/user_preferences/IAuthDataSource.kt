package com.example.user_preferences

import com.example.user_preferences.models.AuthResult
import com.example.user_preferences.models.SignInRequest
import com.example.user_preferences.models.SignUpRequest
import com.example.user_preferences.models.User


interface IAuthDataSource {
    suspend fun signUp(request: SignUpRequest): AuthResult<User>
    suspend fun signIn(request: SignInRequest): AuthResult<User>
    suspend fun signOut()
    suspend fun getCurrentUser(): User?
    suspend fun checkUsernameAvailability(username: String): AuthResult<Boolean>
    suspend fun isUserAuthenticated(): Boolean
}