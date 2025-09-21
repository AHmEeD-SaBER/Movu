package datasources.moviesremotedatasource

import com.example.core_data.models.movies.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface IMoviesRemoteDataSource {
    fun getMovies(): Flow<MoviesResponse>
}