package com.example.data.di

import com.example.data.data_sources.ISplashDataSource
import com.example.data.data_sources.SplashDataSource
import com.example.data.repositories.SplashRepository
import com.example.domain.repositories.ISplashRepository
import org.koin.dsl.module

val splashDataModule = module {
    single<ISplashDataSource> { SplashDataSource(get()) }
    single<ISplashRepository> { SplashRepository(get(), get()) }
}