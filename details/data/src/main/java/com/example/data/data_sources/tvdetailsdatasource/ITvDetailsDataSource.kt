package com.example.data.data_sources.tvdetailsdatasource

import com.example.core_data.models.tvdetails.TvShowDetails
import kotlinx.coroutines.flow.Flow

interface ITvDetailsDataSource {
    fun getTvDetails(tvId: Int): Flow<TvShowDetails>
}
