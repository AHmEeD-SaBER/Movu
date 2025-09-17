package com.example.ui.signup

import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState


class SignUpContract {

    sealed class Event : UiEvent {
        data class OnEmailChanged(val email: String) : Event()
        data class OnPasswordChanged(val password: String) : Event()
        data class OnNameChanged(val name: String) : Event()
        data class OnConfirmPasswordChanged(val confirmPassword: String) : Event()
        object OnSignUpClick : Event()
        object OnSignInNavigate : Event()
        object TogglePasswordVisibility : Event()
        object ToggleConfirmPasswordVisibility : Event()
    }

    data class State(
        val email: String = "",
        val password: String = "",
        val name: String = "",
        val confirmPassword: String = "",
        val isLoading: Boolean = false,
        val emailError: Int? = null,
        val passwordError: Int? = null,
        val nameError: Int? = null,
        val confirmPasswordError: Int? = null,
        val isSignUpEnabled: Boolean = false,
        val isPasswordVisible: Boolean = false,
        val isConfirmPasswordVisible: Boolean = false,
    ) : UiState

    sealed class Effect : UiEffect {
        object NavigateToSignIn : Effect()
        object NavigateToHome : Effect()
        data class ShowError(val messageRes: Int) : Effect()
        data class ShowSuccess(val messageRes: Int) : Effect()
    }
}
