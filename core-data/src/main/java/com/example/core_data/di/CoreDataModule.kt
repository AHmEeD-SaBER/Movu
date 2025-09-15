package com.example.core_data.di

import com.example.core_data.utils.INetworkMonitor
import com.example.core_data.utils.NetworkMonitor
import org.koin.dsl.module

val coreDataModule = module{
    single<INetworkMonitor>{ NetworkMonitor(get()) }

}