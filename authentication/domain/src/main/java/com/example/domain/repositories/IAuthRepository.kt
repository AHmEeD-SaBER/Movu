package com.example.domain.repositories

import com.example.domain.models.AuthResult
import com.example.domain.models.SignInRequest
import com.example.domain.models.SignUpRequest
import com.example.domain.models.UserProfile
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {

    // Authentication operations
    suspend fun signUp(request: SignUpRequest): AuthResult<UserProfile>
    suspend fun signIn(request: SignInRequest): AuthResult<UserProfile>
    suspend fun signOut(): AuthResult<Unit>
    suspend fun getCurrentUserProfile(): AuthResult<UserProfile>
    suspend fun isUserLoggedIn(): Boolean
    suspend fun sendEmailVerification(): AuthResult<Unit>
    suspend fun resetPassword(email: String): AuthResult<Unit>

    // User profile operations
    suspend fun updateUserProfile(updates: Map<String, Any>): AuthResult<Unit>
    suspend fun checkUsernameAvailability(username: String): AuthResult<Boolean>
    suspend fun deleteAccount(): AuthResult<Unit>

    // Real-time user data
    fun observeCurrentUserProfile(): Flow<AuthResult<UserProfile>>

    // Auth state management with SharedPreferences integration
    suspend fun completeAuthentication(userProfile: UserProfile): AuthResult<Unit>
    suspend fun clearAuthenticationState(): AuthResult<Unit>
}