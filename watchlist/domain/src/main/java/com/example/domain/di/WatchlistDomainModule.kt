package com.example.domain.di

import com.example.domain.usecases.removemovies.IRemoveMoviesFromWatchlist
import com.example.domain.usecases.removemovies.RemoveMoviesFromWatchlist
import com.example.domain.usecases.removetvshows.IRemoveTvShowsFromWatchlist
import com.example.domain.usecases.removetvshows.RemoveTvShowsFromWatchlist
import com.example.domain.usecases.watchlistmovies.GetWatchlistMovies
import com.example.domain.usecases.watchlistmovies.IGetWatchlistMovies
import com.example.domain.usecases.watchlisttvshows.GetWatchlistTvShows
import com.example.domain.usecases.watchlisttvshows.IGetWatchlistTvShows
import org.koin.dsl.module

val watchlistDomainModule = module {
    factory<IGetWatchlistMovies> { GetWatchlistMovies(get()) }
    factory<IGetWatchlistTvShows> { GetWatchlistTvShows(get()) }
    factory<IRemoveMoviesFromWatchlist> { RemoveMoviesFromWatchlist(get()) }
    factory<IRemoveTvShowsFromWatchlist> { RemoveTvShowsFromWatchlist(get()) }
}