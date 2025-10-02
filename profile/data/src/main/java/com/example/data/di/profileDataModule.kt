package com.example.data.di

import com.example.data.repositories.UserRepository
import com.example.domain.repositories.IUserRepository
import org.koin.dsl.module

val profileDataModule = module {
    single<IUserRepository> { UserRepository(get(), get(), get()) }
}