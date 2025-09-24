package com.example.user_preferences.auth

import com.example.user_preferences.models.AuthResult
import com.example.user_preferences.models.SignInRequest
import com.example.user_preferences.models.SignUpRequest
import com.example.user_preferences.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseAuthDataSource : IAuthDataSource {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")

    override suspend fun signUp(request: SignUpRequest): AuthResult<User> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(request.email, request.password).await()
            val firebaseUser = authResult.user ?: throw Exception()

            val user = User(
                uid = firebaseUser.uid,
                email = request.email,
                username = request.username
            )

            usersCollection.document(user.uid).set(user).await()
            AuthResult.Success(user)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun signIn(request: SignInRequest): AuthResult<User> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(request.email, request.password).await()
            val firebaseUser = authResult.user ?: throw Exception()

            val userSnapshot = usersCollection.document(firebaseUser.uid).get().await()
            val user = userSnapshot.toObject(User::class.java) ?: throw Exception()
            AuthResult.Success(user)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun signOut() {
        withContext(Dispatchers.IO) {
            firebaseAuth.signOut()
        }
    }

    override suspend fun getCurrentUser(): User? {
        val firebaseUser = firebaseAuth.currentUser ?: return null
        val userSnapshot = usersCollection.document(firebaseUser.uid).get().await()
        return userSnapshot.toObject(User::class.java)
    }

    override suspend fun checkUsernameAvailability(username: String): AuthResult<Boolean> {
        return try {
            val querySnapshot = usersCollection.whereEqualTo("username", username).get().await()
            AuthResult.Success(querySnapshot.isEmpty)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun isUserAuthenticated(): Boolean {
        return withContext(Dispatchers.IO) {
            firebaseAuth.currentUser != null
        }
    }
}
