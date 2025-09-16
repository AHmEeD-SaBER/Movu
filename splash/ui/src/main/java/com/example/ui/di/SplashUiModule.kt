package com.example.ui.di

import com.example.ui.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashUiModule = module {
    viewModel { SplashViewModel(get()) }
}
