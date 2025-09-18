package datasources.moviedetailsdatasource

import models.moviedetails.MovieDetailsResponse
import kotlinx.coroutines.flow.Flow

interface IMovieDetailsDataSource {
    fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse>
}
