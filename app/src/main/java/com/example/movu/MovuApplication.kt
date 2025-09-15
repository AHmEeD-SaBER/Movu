package com.example.movu

import android.app.Application
import com.example.core_data.di.coreDataModule
import com.example.data.di.authDataModule
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
                authDataModule,
                coreDataModule
            )
        }
    }
}
