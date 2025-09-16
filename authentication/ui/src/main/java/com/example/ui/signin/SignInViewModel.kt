package com.example.ui.signin

import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.example.domain.models.DomainAuthResult
import com.example.domain.models.DomainSignInRequest
import com.example.domain.use_cases.login.ISignInUseCase
import com.example.ui.utils.ValidationUtils
import com.movu.authentication.ui.R
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: ISignInUseCase
) : BaseViewModel<SignInContract.Event, SignInContract.State, SignInContract.Effect>() {

    override fun setInitialState(): SignInContract.State {
        return SignInContract.State()
    }

    override fun handleEvent(event: SignInContract.Event) {
        when (event) {
            is SignInContract.Event.OnEmailChanged -> {
                handleEmailChanged(event.email)
            }
            is SignInContract.Event.OnPasswordChanged -> {
                handlePasswordChanged(event.password)
            }
            is SignInContract.Event.OnSignInClick -> {
                handleSignInClick()
            }
            is SignInContract.Event.OnSignUpNavigate -> {
                handleSignUpNavigate()
            }
        }
    }

    private fun handleEmailChanged(email: String) {
        val emailError = ValidationUtils.validateEmail(email)
        setState {
            copy(
                email = email,
                emailError = emailError,
                isSignInEnabled = validateForm(email, uiState.value.password)
            )
        }
    }

    private fun handlePasswordChanged(password: String) {
        val passwordError = ValidationUtils.validateSignInPassword(password)
        setState {
            copy(
                password = password,
                passwordError = passwordError,
                isSignInEnabled = validateForm(uiState.value.email, password)
            )
        }
    }

    private fun handleSignInClick() {
        val currentState = uiState.value

        val emailError = ValidationUtils.validateEmail(currentState.email)
        val passwordError = ValidationUtils.validateSignInPassword(currentState.password)

        if (emailError != null || passwordError != null) {
            setState {
                copy(
                    emailError = emailError,
                    passwordError = passwordError
                )
            }
            return
        }


        setState { copy(isLoading = true) }

        viewModelScope.launch {
            val signInRequest = DomainSignInRequest(
                email = currentState.email,
                password = currentState.password
            )

            when (val result = signInUseCase(signInRequest)) {
                is DomainAuthResult.Success -> {
                    setState { copy(isLoading = false) }
                    setEffect { SignInContract.Effect.ShowSuccess(R.string.sign_in_success) }
                    setEffect { SignInContract.Effect.NavigateToHome }
                }
                is DomainAuthResult.Error -> {
                    setState { copy(isLoading = false) }
                    setEffect {
                        SignInContract.Effect.ShowError(
                            result.exceptionRes
                        )
                    }
                }
                is DomainAuthResult.Loading -> {
                    setState { copy(isLoading = true) }
                }
            }
        }
    }

    private fun handleSignUpNavigate() {
        setEffect { SignInContract.Effect.NavigateToSignUp }
    }

    private fun validateForm(email: String, password: String): Boolean {
        val emailError = ValidationUtils.validateEmail(email)
        val passwordError = ValidationUtils.validateSignInPassword(password)
        return emailError == null && passwordError == null && email.isNotBlank() && password.isNotBlank()
    }
}