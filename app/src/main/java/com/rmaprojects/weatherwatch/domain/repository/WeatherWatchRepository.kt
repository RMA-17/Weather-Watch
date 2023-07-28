package com.rmaprojects.weatherwatch.domain.repository

import com.rmaprojects.weatherwatch.data.source.remote.response.GetWeatherResponse

interface WeatherWatchRepository {
    suspend fun getTodayWeather(
        long: Double,
        lat: Double
    ): GetWeatherResponse

    suspend fun getWeatherByCity(
        cityName: String
    ): GetWeatherResponse
}