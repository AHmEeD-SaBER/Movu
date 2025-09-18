package datasources.tvremotedatasource

import models.tv.TvResponse
import kotlinx.coroutines.flow.Flow

interface ITvRemoteDataSource {
    fun getTvShows(): Flow<TvResponse>
}