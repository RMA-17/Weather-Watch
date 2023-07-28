package com.rmaprojects.weatherwatch.data.repository

import com.rmaprojects.weatherwatch.data.source.remote.RemoteDataSource
import com.rmaprojects.weatherwatch.data.source.remote.network.ApiService
import com.rmaprojects.weatherwatch.data.source.remote.response.GetWeatherResponse
import com.rmaprojects.weatherwatch.domain.repository.WeatherWatchRepository
import javax.inject.Inject

class WeatherWatchRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): WeatherWatchRepository {
    override suspend fun getTodayWeather(long: Double, lat: Double): GetWeatherResponse {
        return remoteDataSource.getWeatherToday(lat, long)
    }

    override suspend fun getWeatherByCity(cityName: String): GetWeatherResponse {
        return remoteDataSource.getWeatherByCity(cityName)
    }
}