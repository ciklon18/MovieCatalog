package com.example.moviecatalog.common.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.launch.presentation.LaunchScreen
import com.example.moviecatalog.login.presentation.LoginScreen
import com.example.moviecatalog.profile.presentation.ProfileScreen
import com.example.moviecatalog.registration.presentation.RegistrationScreen
import com.example.moviecatalog.selectauth.presentation.SelectAuthScreen


@Composable
fun MovieCatalogNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Routes.LaunchScreen.name) {
        composable(route = Routes.LaunchScreen.name) {
            LaunchScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }
        composable(route = Routes.SelectAuthScreen.name) {
            SelectAuthScreen(navController)
        }
        composable(route = Routes.LoginScreen.name) {
            LoginScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }
        composable(route = Routes.RegistrationScreen.name) {
            RegistrationScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }
        composable(route = Routes.ProfileScreen.name) {
            ProfileScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }
    }
}