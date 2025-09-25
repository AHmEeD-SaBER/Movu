package com.example.navigation.di

import com.example.navigation.mainnavigation.BottomNavigationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val navigationModule = module {
    viewModel { BottomNavigationViewModel() }
}