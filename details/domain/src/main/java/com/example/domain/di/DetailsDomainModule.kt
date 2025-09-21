package com.example.domain.di

import com.example.domain.usecases.mediadetails.GetMediaDetailsUseCase
import com.example.domain.usecases.mediadetails.IGetMediaDetailsUseCase
import com.example.domain.usecases.moviedetails.GetMovieDetailsUseCase
import com.example.domain.usecases.moviedetails.IGetMovieDetailsUseCase
import com.example.domain.usecases.tvdetails.GetTvDetailsUseCase
import com.example.domain.usecases.tvdetails.IGetTvDetailsUseCase
import org.koin.dsl.module

val detailsDomainModule = module {
    factory<IGetTvDetailsUseCase> { GetTvDetailsUseCase(get()) }
    factory<IGetMediaDetailsUseCase> { GetMediaDetailsUseCase(get(), get()) }
    factory<IGetMovieDetailsUseCase> { GetMovieDetailsUseCase(get()) }
}