package com.rmaprojects.weatherwatch.data.source.remote

import com.rmaprojects.weatherwatch.data.source.remote.network.ApiService
import com.rmaprojects.weatherwatch.data.source.remote.response.GetWeatherResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getWeatherToday(
        lat: Double,
        long: Double,
    ): GetWeatherResponse {
        return apiService.getWeather(lat, long)
    }

    suspend fun getWeatherByCity(
        cityName: String
    ): GetWeatherResponse {
        return apiService.getWeather(cityName)
    }
}