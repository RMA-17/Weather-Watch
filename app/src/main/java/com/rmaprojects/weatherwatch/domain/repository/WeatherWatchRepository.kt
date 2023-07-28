package com.rmaprojects.weatherwatch.domain.repository

import com.rmaprojects.weatherwatch.data.source.local.entity.WeatherEntity
import com.rmaprojects.weatherwatch.data.source.remote.response.GetWeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherWatchRepository {
    suspend fun getTodayWeather(
        long: Double,
        lat: Double
    ): GetWeatherResponse

    suspend fun getWeatherByCity(
        cityName: String
    ): GetWeatherResponse

    suspend fun insertWeather(withLocationWeatherEntity: WeatherEntity, locationWithCityList: List<WeatherEntity>)

    fun getAllCachedData(): Flow<List<WeatherEntity>>

    suspend fun clearAll()
}