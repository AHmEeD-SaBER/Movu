package com.example.domain.di

import com.example.domain.use_cases.get_count.GetUserUseCase
import com.example.domain.use_cases.get_count.IGetUserUseCase
import com.example.domain.use_cases.get_user.GetWatchlistCount
import com.example.domain.use_cases.get_user.IGetWatchlistCount
import com.example.domain.usecases.logout.ILogoutUseCase
import com.example.domain.usecases.logout.LogoutUseCase
import org.koin.dsl.module

val profileDomainModule = module {
    factory<IGetWatchlistCount> { GetWatchlistCount(get()) }
    factory<IGetUserUseCase> { GetUserUseCase(get()) }

    factory<ILogoutUseCase> { LogoutUseCase(get()) }
}