package com.example.movu

import android.app.Application
import authUiModule
import com.example.core_data.di.coreDataModule
import com.example.data.di.authDataModule
import com.example.data.di.detailsDataModule
import com.example.data.di.splashDataModule
import com.example.data.di.watchlistDataModule
import com.example.domain.di.authDomainModule
import com.example.domain.di.detailsDomainModule
import com.example.domain.di.homeDomainModule
import com.example.domain.di.searchDomainModule
import com.example.domain.di.splashDomainModule
import com.example.domain.di.watchlistDomainModule
import com.example.navigation.di.navigationModule
import com.example.ui.di.detailsUiModule
import com.example.ui.di.homeUiModule
import com.example.ui.di.searchUiModule
import com.example.ui.di.splashUiModule
import com.example.ui.di.watchlistUiModule
import com.example.user_preferences.di.firebaseModule
import di.homeDataModule
import com.example.data.di.searchDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovuApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MovuApplication)
            modules(
                // Data layer modules
                coreDataModule,
                navigationModule,

                firebaseModule,

                authDataModule,
                authDomainModule,
                authUiModule,

                splashUiModule,
                splashDataModule,
                splashDomainModule,

                homeDataModule,
                homeDomainModule,
                homeUiModule,

                detailsDataModule,
                detailsDomainModule,
                detailsUiModule,

                watchlistUiModule,
                watchlistDomainModule,
                watchlistDataModule,

                searchUiModule,
                searchDomainModule,
                searchDataModule



            )
        }
    }
}
