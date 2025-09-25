package com.example.user_preferences.di

import com.example.user_preferences.auth.FirebaseAuthDataSource
import com.example.user_preferences.auth.IAuthDataSource
import com.example.user_preferences.favorites.FirebaseWatchlistDataSource
import com.example.user_preferences.favorites.IWatchlistDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

val firebaseModule = module {

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<FirebaseFirestore> {
        FirebaseFirestore.getInstance()
    }

    single<IAuthDataSource> {
        FirebaseAuthDataSource()
    }

    single<IWatchlistDataSource> {
        FirebaseWatchlistDataSource(
            firestore = get(),
            auth = get()
        )
    }
}
