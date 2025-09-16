package com.example.domain.di

import com.example.domain.use_cases.ISplashUseCase
import com.example.domain.use_cases.SplashUseCase
import org.koin.dsl.module

val splashDomainModule = module {

    factory<ISplashUseCase> { SplashUseCase(get()) }

}