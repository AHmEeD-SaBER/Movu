package com.example.ui

import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.example.domain.use_cases.ISplashUseCase
import com.example.ui.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashUseCase: ISplashUseCase
) : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    override fun setInitialState(): SplashContract.State {
        return SplashContract.State()
    }

    override fun handleEvent(event: SplashContract.Event) {
        when (event) {
            is SplashContract.Event.CheckAuthStatus -> {
                handleCheckAuthStatus()
            }
        }
    }

    private fun handleCheckAuthStatus() {
        setState { copy(isLoading = true) }

        viewModelScope.launch {
            delay(Constants.SPLASH_TIMEOUT)

            try {
                val isUserLoggedIn = splashUseCase()

                setState { copy(isLoading = false) }

                if (isUserLoggedIn) {
                    setEffect { SplashContract.Effect.NavigateToHome }
                } else {
                    setEffect { SplashContract.Effect.NavigateToAuth }
                }
            } catch (e: Exception) {
                setState { copy(isLoading = false) }
                setEffect { SplashContract.Effect.NavigateToAuth }
            }
        }
    }
}