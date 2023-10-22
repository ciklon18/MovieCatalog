package com.example.moviecatalog.commons.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.commons.validation.usecases.ValidateLoginUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidatePasswordUseCase
import com.example.moviecatalog.launch.presentation.LaunchScreen
import com.example.moviecatalog.login.presentation.LoginScreen
import com.example.moviecatalog.login.presentation.LoginScreenViewModel
import com.example.moviecatalog.selectauth.presentation.SelectAuthScreen


@Composable
fun MovieCatalogNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Routes.LaunchScreen.name) {
        composable(route = Routes.LaunchScreen.name) {
            LaunchScreen(navController)
        }
        composable(route = Routes.SelectAuthScreen.name) {
            SelectAuthScreen(navController)
        }
        composable(route = Routes.LoginScreen.name) {
            LoginScreen(
                navController = navController,
                viewModel = LoginScreenViewModel(
                    ValidateLoginUseCase(),
                    ValidatePasswordUseCase()
                )
            )
        }

    }
}