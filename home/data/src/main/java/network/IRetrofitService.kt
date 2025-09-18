package network


import com.example.data.utils.Constants
import models.moviedetails.MovieDetailsResponse
import models.movies.MoviesResponse
import models.tv.TvResponse
import models.tvdetails.TvShowDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRetrofitService {
    @GET(Constants.EndPoints.MOVIES)
    suspend fun getPopularMovies(@Query(Constants.QueryParams.API_KEY) apiKey: String = Constants.API_KEY): MoviesResponse

    @GET(Constants.EndPoints.TV_SHOW)
    suspend fun getTrendingTVShows(@Query(Constants.QueryParams.API_KEY) apiKey: String = Constants.API_KEY): TvResponse

    @GET(Constants.EndPoints.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path(Constants.QueryParams.MOVIE_ID) movieId: Int,
        @Query(Constants.QueryParams.API_KEY) apiKey: String = Constants.API_KEY
    ): MovieDetailsResponse

    @GET(Constants.EndPoints.TV_DETAILS)
    suspend fun getTvDetails(
        @Path(Constants.QueryParams.TV_ID) tvId: Int,
        @Query(Constants.QueryParams.API_KEY) apiKey: String = Constants.API_KEY
    ): TvShowDetails
}