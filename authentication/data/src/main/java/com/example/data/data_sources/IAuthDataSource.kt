package com.example.data.data_sources

import com.google.firebase.auth.FirebaseUser
import com.example.data.models.AuthResult
import com.example.data.models.SignInRequest
import com.example.data.models.SignUpRequest
import com.example.data.models.UserDto
import kotlinx.coroutines.flow.Flow

interface IAuthDataSource {

    // Firebase Auth operations
    suspend fun signUp(request: SignUpRequest): AuthResult<FirebaseUser>
    suspend fun signIn(request: SignInRequest): AuthResult<FirebaseUser>
    suspend fun signOut(): AuthResult<Unit>
    suspend fun getCurrentUser(): FirebaseUser?

    suspend fun saveUserToFirestore(userDto: UserDto): AuthResult<Unit>
    suspend fun getUserFromFirestore(uid: String): AuthResult<UserDto>
    suspend fun checkUsernameAvailability(username: String): AuthResult<Boolean>
}