package com.example.ui.di

import com.example.ui.screen.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchUiModule = module {
    viewModel { SearchViewModel(get(), get()) }
}
