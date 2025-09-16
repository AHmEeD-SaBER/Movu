package com.example.data.di


import AuthRepository
import com.example.domain.repositories.IAuthRepository
import org.koin.dsl.module

val authDataModule = module {
    single<IAuthRepository> { AuthRepository(get(), get()) }
}
