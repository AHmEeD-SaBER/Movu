package com.example.data.di

import com.example.data.repositories.WatchlistRepository
import com.example.domain.repositories.IWatchlistRepository
import org.koin.dsl.module

val watchlistDataModule = module {
    single<IWatchlistRepository> { WatchlistRepository(get(), get()) }
}