package com.example.user_preferences.di

import com.example.user_preferences.FirebaseAuthDataSource
import com.example.user_preferences.IAuthDataSource
import org.koin.dsl.module

val authDataModule = module {
    single<IAuthDataSource> { FirebaseAuthDataSource() }
}