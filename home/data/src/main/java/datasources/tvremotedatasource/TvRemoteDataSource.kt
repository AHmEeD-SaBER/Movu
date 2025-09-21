package datasources.tvremotedatasource



import com.example.core_data.models.tv.TvResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.core_data.network.IRetrofitService

class TvRemoteDataSource(private val api: IRetrofitService) : ITvRemoteDataSource {
    override fun getTvShows(): Flow<TvResponse> {
        return flow {
            emit(api.getTrendingTVShows())
        }
    }
}