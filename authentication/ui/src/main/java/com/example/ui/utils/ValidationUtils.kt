package com.example.ui.utils

import android.util.Patterns
import com.movu.authentication.ui.R

class ValidationUtils {
    companion object {

        fun validateEmail(email: String): Int? {
            return when {
                email.isBlank() -> R.string.error_email_required
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.error_email_invalid_format
                else -> null
            }
        }

        fun validateSignInPassword(password: String): Int? {
            return when {
                password.isBlank() -> R.string.error_password_required
                password.length < 6 -> R.string.error_password_min_length
                else -> null
            }
        }

        fun validateSignUpPassword(password: String): Int? {
            return when {
                password.isBlank() -> R.string.error_password_required
                password.length < 6 -> R.string.error_password_min_length
                !password.any { it.isDigit() } -> R.string.error_password_needs_digit
                !password.any { it.isUpperCase() } -> R.string.error_password_needs_uppercase
                !password.any { it.isLowerCase() } -> R.string.error_password_needs_lowercase
                else -> null
            }
        }

        fun validateConfirmPassword(password: String, confirmPassword: String): Int? {
            return when {
                confirmPassword.isBlank() -> R.string.error_confirm_password_required
                password != confirmPassword -> R.string.error_passwords_do_not_match
                else -> null
            }
        }

        fun validateName(name: String): Int? {
            return when {
                name.isBlank() -> R.string.error_username_required
                name.length < 3 -> R.string.error_username_min_length
                else -> null
            }
        }
    }
}