package com.rmaprojects.weatherwatch.domain.model

data class WeatherModel(
    val longitude: Double,
    val latitude: Double,
    val weatherStatus: String,
    val weatherIconUrl: String,
    val temperature: Double,
    val humidity: Int,
    val windSpeed: Double,
    val placeName: String
)
