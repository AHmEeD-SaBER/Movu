package datasources.moviedetailsdatasource


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import models.moviedetails.MovieDetailsResponse
import network.IRetrofitService

class MovieDetailsDataSource(private val api: IRetrofitService) : IMovieDetailsDataSource {
    override fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse> {
        return flow {
            emit(api.getMovieDetails(movieId))
        }
    }
}