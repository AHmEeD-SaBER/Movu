package com.example.ui.signin

import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState


class SignInContract {

    sealed class Event : UiEvent {
        data class OnEmailChanged(val email: String) : Event()
        data class OnPasswordChanged(val password: String) : Event()
        object OnSignInClick : Event()
        object OnSignUpNavigate : Event()
        object TogglePasswordVisibility : Event()
    }

    data class State(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val emailError: Int? = null,
        val passwordError: Int? = null,
        val isSignInEnabled: Boolean = false,
        val isPasswordVisible: Boolean = false,
        val isFormValid: Boolean = false
    ) : UiState

    sealed class Effect : UiEffect {
        object NavigateToSignUp : Effect()
        object NavigateToHome : Effect()
        data class ShowError(val messageRes: Int) : Effect()
        data class ShowSuccess(val messageRes: Int) : Effect()
    }
}
