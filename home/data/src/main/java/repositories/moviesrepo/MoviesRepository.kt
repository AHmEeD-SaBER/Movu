package com.example.data.repositories.moviesrepo

import datasources.moviesremotedatasource.IMoviesRemoteDataSource
import com.example.core_data.utils.INetworkMonitor
import com.example.domain.models.MediaResult
import com.example.domain.models.MediaItemsResponse
import com.example.domain.models.MediaError
import com.example.home.data.utils.toDomainModel
import com.example.domain.repositories.IMoviesRepository
import com.example.core_data.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepository(
    private val dataSource: IMoviesRemoteDataSource,
    private val networkMonitor: INetworkMonitor
) : IMoviesRepository {
    override fun getMovies(): Flow<MediaResult<MediaItemsResponse>> = flow {
        try {
            if (!networkMonitor.isNetworkAvailable()) {
                emit(MediaResult.Error(MediaError(
                    titleRes = R.string.error_title_network,
                    subtitleRes = R.string.error_subtitle_network
                )))
                return@flow
            }

            dataSource.getMovies().collect { moviesResponse ->
                if (moviesResponse.movies.isNullOrEmpty()) {
                    emit(MediaResult.Error(MediaError(
                        titleRes = R.string.error_title_no_data,
                        subtitleRes = R.string.error_subtitle_no_data
                    )))
                } else {
                    val domainResponse = moviesResponse.toDomainModel()
                    emit(MediaResult.Success(domainResponse))
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