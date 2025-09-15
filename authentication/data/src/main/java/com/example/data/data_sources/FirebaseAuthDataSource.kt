package com.example.data.data_sources

import com.example.domain.models.AuthResult
import com.example.domain.models.SignInRequest
import com.example.domain.models.SignUpRequest
import com.example.domain.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.withContext

class FirebaseAuthDataSource : IAuthDataSource {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")

    override suspend fun signUp(request: SignUpRequest): AuthResult<User> {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(request: SignInRequest): AuthResult<User> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): User? {
        TODO("Not yet implemented")
    }

    override suspend fun checkUsernameAvailability(username: String): AuthResult<Boolean> {
        TODO("Not yet implemented")
    }


}



