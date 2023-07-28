package com.rmaprojects.weatherwatch.presentation.views.home.states

sealed class HomeStates {
    object Loading: HomeStates()
    data class Error(val message: String): HomeStates()
    object Success: HomeStates()
}