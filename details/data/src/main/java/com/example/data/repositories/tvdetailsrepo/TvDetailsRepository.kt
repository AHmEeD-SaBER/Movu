package com.example.data.repositories.tvdetailsrepo


import com.example.core_data.utils.INetworkMonitor
import com.example.core_data.R
import com.example.data.data_sources.tvdetailsdatasource.ITvDetailsDataSource
import com.example.domain.DetailsError
import com.example.domain.DetailsResult
import com.example.domain.Tv
import com.example.domain.repositories.ITvDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvDetailsRepository(
    private val dataSource: ITvDetailsDataSource,
    private val networkMonitor: INetworkMonitor
) : ITvDetailsRepository {
    override fun getTvDetails(tvId: Int): Flow<DetailsResult<Tv>> = flow {
        try {
            if (!networkMonitor.isNetworkAvailable()) {
                emit(DetailsResult.Error(DetailsError(
                    titleRes = R.string.error_title_network,
                    subtitleRes = R.string.error_subtitle_network
                )))
                return@flow
            }

            dataSource.getTvDetails(tvId).collect { tvDetailsResponse ->
                if (tvDetailsResponse.id == null || tvDetailsResponse.name.isNullOrEmpty()) {
                    emit(DetailsResult.Error(DetailsError(
                        titleRes = R.string.error_title_no_data,
                        subtitleRes = R.string.error_subtitle_no_data
                    )))
                } else {
                    val domainTv = tvDetailsResponse.toDomainModel()
                    emit(DetailsResult.Success(domainTv))
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
