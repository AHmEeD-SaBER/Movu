package com.example.domain.di


import com.example.domain.usecases.movies.GetMoviesUseCase
import com.example.domain.usecases.movies.IGetMoviesUseCase
import com.example.domain.usecases.tv.GetTvShowsUseCase
import com.example.domain.usecases.tv.IGetTvShowsUseCase
import org.koin.dsl.module

val homeDomainModule = module {
    factory<IGetMoviesUseCase> { GetMoviesUseCase(get()) }
    factory<IGetTvShowsUseCase> { GetTvShowsUseCase(get()) }


}