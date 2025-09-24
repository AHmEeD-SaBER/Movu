package com.example.data.repositories.tvdetailsrepo


import com.example.core_data.utils.INetworkMonitor
import com.example.core_data.R
import com.example.data.data_sources.tvdetailsdatasource.ITvDetailsDataSource
import com.example.data.data_sources.creditsdatasource.ICreditsDataSource
import com.example.data.data_sources.videosdatasource.IVideosDataSource
import com.example.details.data.utils.toDomainModel
import com.example.details.data.utils.toCredits
import com.example.details.data.utils.getTrailerLink
import com.example.domain.DetailsError
import com.example.domain.DetailsResult
import com.example.domain.Tv
import com.example.domain.repositories.ITvDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.combine

class TvDetailsRepository(
    private val dataSource: ITvDetailsDataSource,
    private val creditsDataSource: ICreditsDataSource,
    private val networkMonitor: INetworkMonitor,
    private val videosDataSource: IVideosDataSource
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

            // Combine TV details, credits, and videos
            combine(
                dataSource.getTvDetails(tvId),
                creditsDataSource.getTvCredits(tvId),
                videosDataSource.getTvVideos(tvId)
            ) { tvDetailsResponse, creditsResponse, videosResponse ->
                if (tvDetailsResponse.id == null || tvDetailsResponse.name.isNullOrEmpty()) {
                    DetailsResult.Error(DetailsError(
                        titleRes = R.string.error_title_no_data,
                        subtitleRes = R.string.error_subtitle_no_data
                    ))
                } else {
                    val credits = creditsResponse.toCredits()
                    val trailerLink = videosResponse.getTrailerLink()
                    val domainTv = tvDetailsResponse.toDomainModel(credits, trailerLink)
                    DetailsResult.Success(domainTv)
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
