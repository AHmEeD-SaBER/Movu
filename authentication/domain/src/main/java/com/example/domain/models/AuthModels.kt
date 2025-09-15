package com.example.domain.models

import kotlinx.coroutines.flow.Flow

/**
 * Generic result wrapper for authentication operations
 */
sealed class AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error(val message: String, val exception: Throwable? = null) : AuthResult<Nothing>()
}

/**
 * Sign up request model for domain layer
 */
data class SignUpRequest(
    val email: String,
    val password: String,
    val username: String,
    val displayName: String? = null
)

/**
 * Sign in request model for domain layer
 */
data class SignInRequest(
    val email: String,
    val password: String
)

/**
 * User profile model for domain layer
 */
data class UserProfile(
    val uid: String,
    val email: String,
    val username: String,
    val displayName: String?,
    val profileImageUrl: String?,
    val isEmailVerified: Boolean,
    val authProvider: AuthProvider,
    val createdAt: Long, // Timestamp in milliseconds
    val updatedAt: Long  // Timestamp in milliseconds
)

/**
 * Authentication provider enum
 */
enum class AuthProvider {
    EMAIL,
    GOOGLE,
    FACEBOOK,
    APPLE
}
