package di

import com.example.domain.repositories.IMoviesRepository
import com.example.domain.repositories.ITvRepository
import com.example.data.repositories.moviesrepo.MoviesRepository
import com.example.data.repositories.tvrepo.TvRepository
import com.example.core_data.utils.Constants
import datasources.moviesremotedatasource.IMoviesRemoteDataSource
import datasources.moviesremotedatasource.MoviesRemoteDataSource
import com.example.core_data.network.IRetrofitService
import datasources.tvremotedatasource.ITvRemoteDataSource
import datasources.tvremotedatasource.TvRemoteDataSource
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


    single<ITvRemoteDataSource> {
        TvRemoteDataSource(api = get())
    }

    // Repositories
    single<IMoviesRepository> {
        MoviesRepository(
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


}
