package com.example.data.repositories.tvrepo

import datasources.tvremotedatasource.ITvRemoteDataSource
import com.example.core_data.utils.INetworkMonitor
import com.example.domain.models.MediaResult
import com.example.domain.models.MediaItemsResponse
import com.example.domain.models.MediaError
import com.example.data.utils.toDomainModel
import com.example.domain.repositories.ITvRepository
import com.example.data.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvRepository(
    private val dataSource: ITvRemoteDataSource,
    private val networkMonitor: INetworkMonitor
) : ITvRepository {
    override fun getTvShows(): Flow<MediaResult<MediaItemsResponse>> = flow {
        try {
            if (!networkMonitor.isNetworkAvailable()) {
                emit(MediaResult.Error(MediaError(
                    titleRes = R.string.error_title_network,
                    subtitleRes = R.string.error_subtitle_network
                )))
                return@flow
            }

            dataSource.getTvShows().collect { tvResponse ->
                if (tvResponse.tvShows.isNullOrEmpty()) {
                    emit(MediaResult.Error(MediaError(
                        titleRes = R.string.error_title_no_data,
                        subtitleRes = R.string.error_subtitle_no_data
                    )))
                } else {
                    val domainResponse = tvResponse.toDomainModel()
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
