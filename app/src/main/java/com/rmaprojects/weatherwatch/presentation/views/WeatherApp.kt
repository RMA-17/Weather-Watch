package com.rmaprojects.weatherwatch.presentation.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rmaprojects.weatherwatch.presentation.navigation.Screen
import com.rmaprojects.weatherwatch.presentation.views.home.HomeScreen

@Composable
fun WeatherWatchApp(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
    }
}