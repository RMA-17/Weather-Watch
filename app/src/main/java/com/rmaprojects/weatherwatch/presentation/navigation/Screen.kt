package com.rmaprojects.weatherwatch.presentation.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
}