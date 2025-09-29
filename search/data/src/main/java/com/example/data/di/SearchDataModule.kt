package com.example.data.di

import com.example.domain.repositories.ISearchRepository
import com.example.data.data_sources.ISearchRemoteDataSource
import com.example.data.data_sources.SearchRemoteDataSource
import com.example.data.repositories.SearchRepository
import org.koin.dsl.module

val searchDataModule = module {

    // Data Sources
    single<ISearchRemoteDataSource> {
        SearchRemoteDataSource(retrofitService = get())
    }

    // Repository
    single<ISearchRepository> {
        SearchRepository(
            searchRemoteDataSource = get(),
            networkMonitor = get()
        )
    }
}
