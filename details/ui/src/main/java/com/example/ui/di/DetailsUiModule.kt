package com.example.ui.di

import com.example.ui.DetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val detailsUiModule = module {
    viewModel {
        DetailsViewModel(
            getMediaDetailsUseCase = get(),
            movieDetailsRepository = get(),
            tvDetailsRepository = get(),
            addMovieReviewUseCase = get(),
            getMovieReviewsUseCase = get(),
            getUserMovieReviewUseCase = get(),
            addTvReviewUseCase = get(),
            getTvReviewsUseCase = get(),
            getUserTvReviewUseCase = get(),
            savedStateHandle = get()
        )
    }
}