package com.example.data.repositories.moviedetailsrepo

import com.example.core_data.utils.INetworkMonitor
import com.example.domain.repositories.IMovieDetailsRepository
import com.example.core_data.R
import com.example.data.data_sources.moviedetailsdatasource.IMovieDetailsDataSource
import com.example.domain.DetailsError
import com.example.domain.DetailsResult
import com.example.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieDetailsRepository(
    private val dataSource: IMovieDetailsDataSource,
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

            dataSource.getMovieDetails(movieId).collect { movieDetailsResponse ->
                if (movieDetailsResponse.id == null || movieDetailsResponse.title.isNullOrEmpty()) {
                    emit(DetailsResult.Error(DetailsError(
                        titleRes = R.string.error_title_no_data,
                        subtitleRes = R.string.error_subtitle_no_data
                    )))
                } else {
                    val domainMovie = movieDetailsResponse.toDomainModel()
                    emit(DetailsResult.Success(domainMovie))
                }
            }
        } catch (e: Exception) {
            emit(DetailsResult.Error(DetailsError(
                titleRes = R.string.error_title_unknown,
                subtitleRes = R.string.error_subtitle_unknown
            )))
        }
    }
}
