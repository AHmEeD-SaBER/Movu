# Authentication Data Module

This module provides a complete authentication solution using Firebase Auth for authentication and Firestore for storing user data including usernames.

## 🏗️ Architecture

### Data Flow
1. **Firebase Auth** - Handles email/password authentication
2. **Firestore** - Stores user profile data including username
3. **SharedPreferences** - Fast local auth state checking via UserPreferences module
4. **Repository Pattern** - Clean abstraction layer

### Components Created

#### Data Models
- `UserDto` - Firestore user document model
- `AuthResult<T>` - Sealed class for handling auth operation results
- `SignUpRequest` & `SignInRequest` - Authentication request models
- `UserProfile` - Domain-friendly user profile model

#### Data Sources
- `IAuthDataSource` - Interface defining all auth operations
- `FirebaseAuthDataSource` - Firebase Auth + Firestore implementation

#### Repositories
- `IAuthRepository` - Repository interface
- `AuthRepositoryImpl` - Repository implementation with business logic

#### Utilities
- `AuthUtils` - Validation and error handling utilities
- `AuthDataModule` - Koin dependency injection configuration

## 🚀 Key Features

### Complete Authentication Flow
```kotlin
// Sign Up (Creates Firebase Auth user + Firestore document + SharedPreferences)
val signUpRequest = SignUpRequest("email@example.com", "password123", "username", "Display Name")
val result = authRepository.signUp(signUpRequest)
```

### Username Storage & Validation
- Usernames are stored in Firestore collection "users"
- Automatic username availability checking
- Username validation (3-20 chars, alphanumeric + underscore)

### Real-time User Data
- Firestore real-time listeners for user profile changes
- Automatic UI updates when user data changes

### Fast Authentication State Checking
- Integration with UserPreferences module
- Instant auth state checking using SharedPreferences
- Offline-capable authentication state

### Comprehensive Error Handling
- Firebase error code mapping to user-friendly messages
- Input validation with detailed error messages
- Proper exception handling throughout

## 🔧 Usage Examples

### 1. Sign Up New User
```kotlin
class AuthViewModel : KoinComponent {
    private val authRepository: IAuthRepository by inject()
    
    suspend fun signUp(email: String, password: String, username: String) {
        // Validation is built-in
        val request = SignUpRequest(email, password, username)
        
        when (val result = authRepository.signUp(request)) {
            is AuthResult.Success -> {
                val user = result.data
                // User created in Firebase Auth + Firestore + SharedPreferences
                navigateToMainScreen()
            }
            is AuthResult.Error -> {
                showError(AuthUtils.getFirebaseErrorMessage(result.exception))
            }
        }
    }
}
```

### 2. Sign In Existing User
```kotlin
suspend fun signIn(email: String, password: String) {
    val request = SignInRequest(email, password)
    
    when (val result = authRepository.signIn(request)) {
        is AuthResult.Success -> {
            val user = result.data
            // Gets user from Firebase Auth + Firestore data + saves to SharedPreferences
            showWelcomeMessage(user.displayName)
        }
        is AuthResult.Error -> {
            showError(AuthUtils.getFirebaseErrorMessage(result.exception))
        }
    }
}
```

### 3. Check Authentication State (Lightning Fast)
```kotlin
suspend fun checkAuthState() {
    // This uses SharedPreferences - instant check
    val isLoggedIn = authRepository.isUserLoggedIn()
    
    if (isLoggedIn) {
        navigateToMainScreen()
    } else {
        navigateToLoginScreen()
    }
}
```

### 4. Real-time User Data Observation
```kotlin
// In your ViewModel or Repository
authRepository.observeCurrentUserProfile().collect { result ->
    when (result) {
        is AuthResult.Success -> {
            val user = result.data
            updateUI(user) // UI automatically updates when user data changes in Firestore
        }
        is AuthResult.Error -> handleError(result.exception)
    }
}
```

### 5. Update User Profile
```kotlin
suspend fun updateProfile(displayName: String, profileImageUrl: String) {
    val updates = mapOf(
        "displayName" to displayName,
        "profileImageUrl" to profileImageUrl
    )
    
    authRepository.updateUserProfile(updates)
    // Firestore document is updated with timestamp
    // Real-time listeners automatically notify observers
}
```

## 🔗 Integration with UserPreferences Module

This authentication module seamlessly integrates with your UserPreferences module:

- **After successful login/signup**: Automatically calls `userAuthManager.onSignInLoginComplete(firebaseUser)`
- **During logout**: Automatically calls `userAuthManager.clearAuthData()`
- **Authentication checking**: Uses `userAuthManager.isUserAuthenticated()` for fast checks

## 📱 App Integration

### 1. Add to your Application class:
```kotlin
class MovuApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidContext(this@MovuApplication)
            modules(
                authDataModule,           // Authentication data
                userPreferencesModule     // SharedPreferences auth state
            )
        }
    }
}
```

### 2. In your ViewModels:
```kotlin
class AuthViewModel : ViewModel(), KoinComponent {
    private val authRepository: IAuthRepository by inject()
    
    // Your authentication logic here
}
```

### 3. In your main activity:
```kotlin
class MainActivity : ComponentActivity(), KoinComponent {
    private val authRepository: IAuthRepository by inject()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Fast auth state check
        lifecycleScope.launch {
            if (authRepository.isUserLoggedIn()) {
                navigateToMainScreen()
            } else {
                navigateToAuthScreen()
            }
        }
    }
}
```

## 🔒 Security Features

- Password validation (minimum 6 chars, letters + numbers)
- Email format validation
- Username format validation
- Firebase Auth security rules
- Automatic email verification support
- Password reset functionality
- Account deletion with cleanup

## 🌟 Benefits of This Implementation

1. **Clean Architecture** - Separated concerns with proper abstraction layers
2. **Performance** - Fast auth state checking via SharedPreferences
3. **Real-time** - Live user data updates via Firestore listeners
4. **Offline Support** - Authentication state persists offline
5. **Username Support** - Full username functionality that Firebase Auth lacks
6. **Error Handling** - Comprehensive error handling with user-friendly messages
7. **Validation** - Built-in input validation
8. **Scalable** - Easy to extend with additional auth providers
9. **Testable** - Repository pattern makes unit testing straightforward
10. **Type Safe** - Full Kotlin type safety with sealed classes for results

Your authentication system is now ready to use! It provides everything you need for a complete authentication solution with Firebase Auth + Firestore + local state management.
