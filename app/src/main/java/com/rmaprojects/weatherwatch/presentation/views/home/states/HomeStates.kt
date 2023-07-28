package com.rmaprojects.weatherwatch.presentation.views.home.states

sealed class HomeStates {
    data object Loading: HomeStates()
    data class Error(val message: String): HomeStates()
    data object Success: HomeStates()
}