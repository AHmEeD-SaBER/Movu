package com.example.core_data.network



import com.example.core_data.models.credits.CreditsResponse
import com.example.core_data.models.moviedetails.MovieDetailsResponse
import com.example.core_data.models.movies.MoviesResponse
import com.example.core_data.models.tv.TvResponse
import com.example.core_data.models.tvdetails.TvShowDetails
import com.example.core_data.utils.Constants
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

    @GET(Constants.EndPoints.MOVIE_CREDITS)
    suspend fun getMovieCredits(
        @Path(Constants.QueryParams.MOVIE_ID) movieId: Int,
        @Query(Constants.QueryParams.API_KEY) apiKey: String = Constants.API_KEY
    ): CreditsResponse

    @GET(Constants.EndPoints.TV_CREDITS)
    suspend fun getTvCredits(
        @Path(Constants.QueryParams.TV_ID) tvId: Int,
        @Query(Constants.QueryParams.API_KEY) apiKey: String = Constants.API_KEY
    ): CreditsResponse
}