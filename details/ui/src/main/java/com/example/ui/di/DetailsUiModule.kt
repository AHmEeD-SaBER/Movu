package com.example.ui.di

import com.example.ui.DetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val detailsUiModule = module {
    viewModel { DetailsViewModel(get(), get()) }
}