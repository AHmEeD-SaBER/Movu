package com.example.domain.di

import com.example.domain.usecases.search.ISearchMoviesUseCase
import com.example.domain.usecases.search.ISearchTvShowsUseCase
import com.example.domain.usecases.search.SearchMoviesUseCase
import com.example.domain.usecases.search.SearchTvShowsUseCase
import org.koin.dsl.module

val searchDomainModule = module {
    factory<ISearchMoviesUseCase> { SearchMoviesUseCase(get()) }
    factory<ISearchTvShowsUseCase> { SearchTvShowsUseCase(get()) }
}
