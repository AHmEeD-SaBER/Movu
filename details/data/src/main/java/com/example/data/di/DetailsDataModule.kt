package com.example.data.di

import com.example.data.data_sources.moviedetailsdatasource.IMovieDetailsDataSource
import com.example.data.data_sources.moviedetailsdatasource.MovieDetailsDataSource
import com.example.data.data_sources.tvdetailsdatasource.ITvDetailsDataSource
import com.example.data.data_sources.tvdetailsdatasource.TvDetailsDataSource
import com.example.data.data_sources.creditsdatasource.ICreditsDataSource
import com.example.data.data_sources.creditsdatasource.CreditsDataSource
import com.example.data.data_sources.videosdatasource.IVideosDataSource
import com.example.data.data_sources.videosdatasource.VideosDataSource
import com.example.data.repositories.moviedetailsrepo.MovieDetailsRepository
import com.example.data.repositories.tvdetailsrepo.TvDetailsRepository
import com.example.domain.repositories.IMovieDetailsRepository
import com.example.domain.repositories.ITvDetailsRepository
import org.koin.dsl.module

val detailsDataModule = module {
    single<IMovieDetailsDataSource> {
        MovieDetailsDataSource(api = get())
    }

    single<ITvDetailsDataSource> {
        TvDetailsDataSource(api = get())
    }

    single<ICreditsDataSource> {
        CreditsDataSource(api = get())
    }

    single<IVideosDataSource> {
        VideosDataSource(api = get())
    }


    single<ITvDetailsRepository> {
        TvDetailsRepository(
            dataSource = get(),
            creditsDataSource = get(),
            networkMonitor = get(),
            videosDataSource = get()
        )
    }

    single<IMovieDetailsRepository> {
        MovieDetailsRepository(
            dataSource = get(),
            creditsDataSource = get(),
            networkMonitor = get(),
            videosDataSource = get()
        )
    }
}