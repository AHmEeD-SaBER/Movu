package com.example.data.repositories.moviedetailsrepo

import com.example.core_data.utils.INetworkMonitor
import com.example.domain.repositories.IMovieDetailsRepository
import com.example.core_data.R
import com.example.data.data_sources.moviedetailsdatasource.IMovieDetailsDataSource
import com.example.data.data_sources.creditsdatasource.ICreditsDataSource
import com.example.data.utils.toDomainModel
import com.example.data.utils.toCredits
import com.example.domain.DetailsError
import com.example.domain.DetailsResult
import com.example.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.combine

class MovieDetailsRepository(
    private val dataSource: IMovieDetailsDataSource,
    private val creditsDataSource: ICreditsDataSource,
    private val networkMonitor: INetworkMonitor
) : IMovieDetailsRepository {
    override fun getMovieDetails(movieId: Int): Flow<DetailsResult<Movie>> = flow {
        try {
            if (!networkMonitor.isNetworkAvailable()) {
                emit(DetailsResult.Error(DetailsError(
                    titleRes = R.string.error_title_network,
                    subtitleRes = R.string.error_subtitle_network
                )))
                return@flow
            }

            // Combine movie details and credits
            dataSource.getMovieDetails(movieId).combine(
                creditsDataSource.getMovieCredits(movieId)
            ) { movieDetailsResponse, creditsResponse ->
                if (movieDetailsResponse.id == null || movieDetailsResponse.title.isNullOrEmpty()) {
                    DetailsResult.Error(DetailsError(
                        titleRes = R.string.error_title_no_data,
                        subtitleRes = R.string.error_subtitle_no_data
                    ))
                } else {
                    val credits = creditsResponse.toCredits()
                    val domainMovie = movieDetailsResponse.toDomainModel(credits)
                    DetailsResult.Success(domainMovie)
                }
            }.collect { result ->
                emit(result)
            }
        } catch (e: Exception) {
            emit(DetailsResult.Error(DetailsError(
                titleRes = R.string.error_title_unknown,
                subtitleRes = R.string.error_subtitle_unknown
            )))
        }
    }
}
