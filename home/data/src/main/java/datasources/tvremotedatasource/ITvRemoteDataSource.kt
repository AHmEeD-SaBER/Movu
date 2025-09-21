package datasources.tvremotedatasource

import com.example.core_data.models.tv.TvResponse
import kotlinx.coroutines.flow.Flow

interface ITvRemoteDataSource {
    fun getTvShows(): Flow<TvResponse>
}