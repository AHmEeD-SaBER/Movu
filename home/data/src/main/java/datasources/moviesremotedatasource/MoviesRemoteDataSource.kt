package datasources.moviesremotedatasource

import com.example.core_data.models.movies.MoviesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.core_data.network.IRetrofitService

class MoviesRemoteDataSource(private val api: IRetrofitService) : IMoviesRemoteDataSource {

    override fun getMovies(): Flow<MoviesResponse> {
        return flow {
            emit(api.getPopularMovies())
        }
    }
}