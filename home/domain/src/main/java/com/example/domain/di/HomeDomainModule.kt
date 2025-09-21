package com.example.domain.di

import com.example.domain.repositories.IMoviesRepository
import com.example.domain.usecases.mediadetails.GetMediaDetailsUseCase
import com.example.domain.usecases.mediadetails.IGetMediaDetailsUseCase
import com.example.domain.usecases.moviedetails.GetMovieDetailsUseCase
import com.example.domain.usecases.moviedetails.IGetMovieDetailsUseCase
import com.example.domain.usecases.movies.GetMoviesUseCase
import com.example.domain.usecases.movies.IGetMoviesUseCase
import com.example.domain.usecases.tv.GetTvShowsUseCase
import com.example.domain.usecases.tv.IGetTvShowsUseCase
import com.example.domain.usecases.tvdetails.GetTvDetailsUseCase
import com.example.domain.usecases.tvdetails.IGetTvDetailsUseCase
import org.koin.dsl.module

val homeDomainModule = module {
    factory<IGetMoviesUseCase> { GetMoviesUseCase(get()) }
    factory<IGetTvShowsUseCase> { GetTvShowsUseCase(get()) }
    factory<IGetTvDetailsUseCase> { GetTvDetailsUseCase(get()) }
    factory<IGetMediaDetailsUseCase> { GetMediaDetailsUseCase(get(), get()) }
    factory<IGetMovieDetailsUseCase> { GetMovieDetailsUseCase(get()) }

}