package com.example.data.di

import com.example.data.data_sources.FirebaseAuthDataSource
import com.example.data.data_sources.IAuthDataSource
import com.example.domain.repositories.IAuthRepository
import org.koin.dsl.module

val authDataModule = module {
    single<IAuthDataSource> { FirebaseAuthDataSource() }
//    single<IAuthRepository> { AuthRepository(get()) }
}
