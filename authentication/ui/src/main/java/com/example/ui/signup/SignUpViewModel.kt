package com.example.ui.signup

import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.example.domain.models.DomainAuthResult
import com.example.domain.models.DomainSignUpRequest
import com.example.domain.use_cases.signup.ISignUpUseCase
import com.example.ui.utils.ValidationUtils
import com.movu.authentication.ui.R
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: ISignUpUseCase
) : BaseViewModel<SignUpContract.Event, SignUpContract.State, SignUpContract.Effect>() {

    override fun setInitialState(): SignUpContract.State {
        return SignUpContract.State()
    }

    override fun handleEvent(event: SignUpContract.Event) {
        when (event) {
            is SignUpContract.Event.OnNameChanged -> {
                handleNameChanged(event.name)
            }
            is SignUpContract.Event.OnEmailChanged -> {
                handleEmailChanged(event.email)
            }
            is SignUpContract.Event.OnPasswordChanged -> {
                handlePasswordChanged(event.password)
            }
            is SignUpContract.Event.OnConfirmPasswordChanged -> {
                handleConfirmPasswordChanged(event.confirmPassword)
            }
            is SignUpContract.Event.OnSignUpClick -> {
                handleSignUpClick()
            }
            is SignUpContract.Event.OnSignInNavigate -> {
                handleSignInNavigate()
            }
        }
    }

    private fun handleNameChanged(name: String) {
        val nameError = ValidationUtils.validateName(name)
        setState {
            copy(
                name = name,
                nameError = nameError,
                isSignUpEnabled = validateForm(name, uiState.value.email, uiState.value.password, uiState.value.confirmPassword)
            )
        }
    }

    private fun handleEmailChanged(email: String) {
        val emailError = ValidationUtils.validateEmail(email)
        setState {
            copy(
                email = email,
                emailError = emailError,
                isSignUpEnabled = validateForm(uiState.value.name, email, uiState.value.password, uiState.value.confirmPassword)
            )
        }
    }

    private fun handlePasswordChanged(password: String) {
        val passwordError = ValidationUtils.validateSignUpPassword(password)
        val confirmPasswordError = if (uiState.value.confirmPassword.isNotEmpty()) {
            ValidationUtils.validateConfirmPassword(password, uiState.value.confirmPassword)
        } else {
            uiState.value.confirmPasswordError
        }
        setState {
            copy(
                password = password,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError,
                isSignUpEnabled = validateForm(uiState.value.name, uiState.value.email, password, uiState.value.confirmPassword)
            )
        }
    }

    private fun handleConfirmPasswordChanged(confirmPassword: String) {
        val confirmPasswordError = ValidationUtils.validateConfirmPassword(uiState.value.password, confirmPassword)
        setState {
            copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = confirmPasswordError,
                isSignUpEnabled = validateForm(uiState.value.name, uiState.value.email, uiState.value.password, confirmPassword)
            )
        }
    }

    private fun handleSignUpClick() {
        val currentState = uiState.value

        val nameError = ValidationUtils.validateName(currentState.name)
        val emailError = ValidationUtils.validateEmail(currentState.email)
        val passwordError = ValidationUtils.validateSignUpPassword(currentState.password)
        val confirmPasswordError = ValidationUtils.validateConfirmPassword(currentState.password, currentState.confirmPassword)

        if (nameError != null || emailError != null || passwordError != null || confirmPasswordError != null) {
            setState {
                copy(
                    nameError = nameError,
                    emailError = emailError,
                    passwordError = passwordError,
                    confirmPasswordError = confirmPasswordError
                )
            }
            return
        }

        setState { copy(isLoading = true) }

        viewModelScope.launch {
            val signUpRequest = DomainSignUpRequest(
                email = currentState.email,
                password = currentState.password,
                username = currentState.name
            )

            when (val result = signUpUseCase(signUpRequest)) {
                is DomainAuthResult.Success -> {
                    setState { copy(isLoading = false) }
                    setEffect { SignUpContract.Effect.ShowSuccess(R.string.sign_up_success) }
                    setEffect { SignUpContract.Effect.NavigateToHome }
                }
                is DomainAuthResult.Error -> {
                    setState { copy(isLoading = false) }
                    setEffect {
                        SignUpContract.Effect.ShowError(
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

    private fun handleSignInNavigate() {
        setEffect { SignUpContract.Effect.NavigateToSignIn }
    }

    private fun validateForm(name: String, email: String, password: String, confirmPassword: String): Boolean {
        val nameError = ValidationUtils.validateName(name)
        val emailError = ValidationUtils.validateEmail(email)
        val passwordError = ValidationUtils.validateSignUpPassword(password)
        val confirmPasswordError = ValidationUtils.validateConfirmPassword(password, confirmPassword)
        return nameError == null && emailError == null && passwordError == null && confirmPasswordError == null &&
               name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
    }
}