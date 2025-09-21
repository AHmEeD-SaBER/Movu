package com.example.ui.di

import com.example.ui.home_screen.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeUiModule = module {
    viewModel { HomeViewModel(get(), get()) }
}