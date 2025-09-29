package com.example.core_data.utils


object Constants {
    object EndPoints{
        const val MOVIES = "movie/popular"
        const val TV_SHOW = "trending/tv/week"
        const val MOVIE_DETAILS = "movie/{movie_id}"
        const val TV_DETAILS = "tv/{tv_id}"
        const val MOVIE_CREDITS = "movie/{movie_id}/credits"
        const val TV_CREDITS = "tv/{tv_id}/credits"

        const val MOVIE_VIDEOS = "movie/{movie_id}/videos"
        const val TV_VIDEOS = "tv/{tv_id}/videos"
        const val SEARCH_MOVIE = "search/movie"
        const val SEARCH_TV = "search/tv"

    }
    object QueryParams {
        const val API_KEY = "api_key"
        const val MOVIE_ID = "movie_id"
        const val TV_ID = "tv_id"
        const val QUERY = "query"
    }
    object ErrorMessages {
        const val NETWORK_ERROR = "Please check your internet connection and try again."
        const val DATA_ERROR = "No data found. Please try again later."
        const val UNKNOWN_ERROR = "An unknown error occurred. Please try again later."
    }
    object ErrorTitles {
        const val NETWORK_ERROR = "Network Error"
        const val DATA_ERROR = "No Data Found"
        const val UNKNOWN_ERROR = "Unknown Error"
    }
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "e2669d4bb6934afe70f92afab29a8d4b"
    const val DEFAULT_ANIMATION_DURATION = 250
}