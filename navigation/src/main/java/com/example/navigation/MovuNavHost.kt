package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.core_ui.base.Routes
import com.example.navigation.components.*

@Composable
fun MovuNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: Routes = Routes.Splash
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash Screen
        composable<Routes.Splash> {
            SplashRoute(navController = navController)
        }

        // Sign In Screen
        composable<Routes.SignIn> {
            SignInRoute(navController = navController)
        }

        // Sign Up Screen
        composable<Routes.SignUp> {
            SignUpRoute(navController = navController)
        }

        // Home Screen
        composable<Routes.Home> {
            HomeRoute(navController = navController)
        }

        // Profile Screen
        composable<Routes.Profile> { backStackEntry ->
            val profile = backStackEntry.toRoute<Routes.Profile>()
            ProfileRoute(
                navController = navController,
                userId = profile.userId
            )
        }

        // Movie Detail Screen
        composable<Routes.MovieDetail> { backStackEntry ->
            val movieDetail = backStackEntry.toRoute<Routes.MovieDetail>()
            MovieDetailRoute(
                navController = navController,
                movieId = movieDetail.movieId
            )
        }
    }
}
