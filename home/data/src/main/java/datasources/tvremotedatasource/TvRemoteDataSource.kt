package datasources.tvremotedatasource

import models.tv.TvResponse

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import network.IRetrofitService

class TvRemoteDataSource(private val api: IRetrofitService) : ITvRemoteDataSource {
    override fun getTvShows(): Flow<TvResponse> {
        return flow {
            emit(api.getTrendingTVShows())
        }
    }
}