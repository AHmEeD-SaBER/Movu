package com.example.data.repositories.tvdetailsrepo

import datasources.tvdetailsdatasource.ITvDetailsDataSource
import com.example.core_data.utils.INetworkMonitor
import com.example.domain.models.MediaResult
import com.example.domain.models.Tv
import com.example.domain.models.MediaError
import com.example.data.utils.toDomainModel
import com.example.domain.repositories.ITvDetailsRepository
import com.example.data.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvDetailsRepository(
    private val dataSource: ITvDetailsDataSource,
    private val networkMonitor: INetworkMonitor
) : ITvDetailsRepository {
    override fun getTvDetails(tvId: Int): Flow<MediaResult<Tv>> = flow {
        try {
            if (!networkMonitor.isNetworkAvailable()) {
                emit(MediaResult.Error(MediaError(
                    titleRes = R.string.error_title_network,
                    subtitleRes = R.string.error_subtitle_network
                )))
                return@flow
            }

            dataSource.getTvDetails(tvId).collect { tvDetailsResponse ->
                if (tvDetailsResponse.id == null || tvDetailsResponse.name.isNullOrEmpty()) {
                    emit(MediaResult.Error(MediaError(
                        titleRes = R.string.error_title_no_data,
                        subtitleRes = R.string.error_subtitle_no_data
                    )))
                } else {
                    val domainTv = tvDetailsResponse.toDomainModel()
                    emit(MediaResult.Success(domainTv))
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
