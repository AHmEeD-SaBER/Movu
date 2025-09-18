package com.example.data.repositories.moviedetailsrepo

import datasources.moviedetailsdatasource.IMovieDetailsDataSource
import com.example.core_data.utils.INetworkMonitor
import com.example.domain.models.MediaResult
import com.example.domain.models.Movie
import com.example.domain.models.MediaError
import com.example.data.utils.toDomainModel
import com.example.domain.repositories.IMovieDetailsRepository
import com.example.data.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieDetailsRepository(
    private val dataSource: IMovieDetailsDataSource,
    private val networkMonitor: INetworkMonitor
) : IMovieDetailsRepository {
    override fun getMovieDetails(movieId: Int): Flow<MediaResult<Movie>> = flow {
        try {
            if (!networkMonitor.isNetworkAvailable()) {
                emit(MediaResult.Error(MediaError(
                    titleRes = R.string.error_title_network,
                    subtitleRes = R.string.error_subtitle_network
                )))
                return@flow
            }

            dataSource.getMovieDetails(movieId).collect { movieDetailsResponse ->
                if (movieDetailsResponse.id == null || movieDetailsResponse.title.isNullOrEmpty()) {
                    emit(MediaResult.Error(MediaError(
                        titleRes = R.string.error_title_no_data,
                        subtitleRes = R.string.error_subtitle_no_data
                    )))
                } else {
                    val domainMovie = movieDetailsResponse.toDomainModel()
                    emit(MediaResult.Success(domainMovie))
                }
            }
        } catch (e: Exception) {
            emit(MediaResult.Error(MediaError(
                titleRes = R.string.error_title_unknown,
                subtitleRes = R.string.error_subtitle_unknown
            )))
        }
    }
}
