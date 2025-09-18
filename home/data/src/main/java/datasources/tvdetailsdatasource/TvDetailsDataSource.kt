package datasources.tvdetailsdatasource

import models.tvdetails.TvShowDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import network.IRetrofitService

class TvDetailsDataSource(private val api: IRetrofitService) : ITvDetailsDataSource {
    override fun getTvDetails(tvId: Int): Flow<TvShowDetails> {
        return flow {
            emit(api.getTvDetails(tvId))
        }
    }
}
