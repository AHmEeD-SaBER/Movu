package di

import com.example.domain.repositories.IMovieDetailsRepository
import com.example.domain.repositories.IMoviesRepository
import com.example.domain.repositories.ITvDetailsRepository
import com.example.domain.repositories.ITvRepository
import com.example.data.repositories.moviedetailsrepo.MovieDetailsRepository
import com.example.data.repositories.moviesrepo.MoviesRepository
import com.example.data.repositories.tvdetailsrepo.TvDetailsRepository
import com.example.data.repositories.tvrepo.TvRepository
import com.example.data.utils.Constants
import datasources.moviedetailsdatasource.IMovieDetailsDataSource
import datasources.moviedetailsdatasource.MovieDetailsDataSource
import datasources.moviesremotedatasource.IMoviesRemoteDataSource
import datasources.moviesremotedatasource.MoviesRemoteDataSource
import datasources.tvdetailsdatasource.ITvDetailsDataSource
import datasources.tvdetailsdatasource.TvDetailsDataSource
import datasources.tvremotedatasource.ITvRemoteDataSource
import datasources.tvremotedatasource.TvRemoteDataSource
import network.IRetrofitService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val homeDataModule = module {

    // Network Service
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .build()
    }

    single<IRetrofitService> {
        get<Retrofit>().create(IRetrofitService::class.java)
    }

    // Data Sources
    single<IMoviesRemoteDataSource> {
        MoviesRemoteDataSource(api = get())
    }

    single<IMovieDetailsDataSource> {
        MovieDetailsDataSource(api = get())
    }

    single<ITvRemoteDataSource> {
        TvRemoteDataSource(api = get())
    }

    single<ITvDetailsDataSource> {
        TvDetailsDataSource(api = get())
    }

    // Repositories
    single<IMoviesRepository> {
        MoviesRepository(
            dataSource = get(),
            networkMonitor = get()
        )
    }

    single<IMovieDetailsRepository> {
        MovieDetailsRepository(
            dataSource = get(),
            networkMonitor = get()
        )
    }

    single<ITvRepository> {
        TvRepository(
            dataSource = get(),
            networkMonitor = get()
        )
    }

    single<ITvDetailsRepository> {
        TvDetailsRepository(
            dataSource = get(),
            networkMonitor = get()
        )
    }
}
