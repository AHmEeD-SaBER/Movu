package com.example.ui.di

import com.example.ui.WatchlistViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val watchlistUiModule = module {
    viewModel { WatchlistViewModel(get(), get(), get(), get()) }
}
