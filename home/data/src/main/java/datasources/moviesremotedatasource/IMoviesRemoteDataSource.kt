package datasources.moviesremotedatasource

import models.movies.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface IMoviesRemoteDataSource {
    fun getMovies(): Flow<MoviesResponse>
}