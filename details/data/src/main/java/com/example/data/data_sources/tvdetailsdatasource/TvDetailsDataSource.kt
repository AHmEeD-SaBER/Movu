package com.example.data.data_sources.tvdetailsdatasource

import com.example.core_data.models.tvdetails.TvShowDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.core_data.network.IRetrofitService

class TvDetailsDataSource(private val api: IRetrofitService) : ITvDetailsDataSource {
    override fun getTvDetails(tvId: Int): Flow<TvShowDetails> {
        return flow {
            emit(api.getTvDetails(tvId))
        }
    }
}
