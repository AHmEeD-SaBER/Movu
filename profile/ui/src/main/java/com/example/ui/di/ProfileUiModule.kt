package com.example.ui.di

import com.example.ui.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileUiModule = module {
    viewModel { ProfileViewModel(get(), get()) }
}
