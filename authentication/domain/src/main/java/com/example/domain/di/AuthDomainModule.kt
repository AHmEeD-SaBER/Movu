package com.example.domain.di

import com.example.domain.use_cases.login.ISignInUseCase
import com.example.domain.use_cases.login.SignInUseCase
import com.example.domain.use_cases.signup.ISignUpUseCase
import com.example.domain.use_cases.signup.SignUpUseCase
import org.koin.dsl.module

val authDomainModule = module {
    factory<ISignInUseCase> { SignInUseCase(get()) }
    factory<ISignUpUseCase> { SignUpUseCase(get()) }
}
