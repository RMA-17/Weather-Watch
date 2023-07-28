package com.rmaprojects.weatherwatch.domain.usecases

import com.rmaprojects.weatherwatch.data.source.local.entity.WeatherEntity
import com.rmaprojects.weatherwatch.domain.model.WeatherModel
import com.rmaprojects.weatherwatch.domain.status.ResponseStatus
import kotlinx.coroutines.flow.Flow


interface WeatherWatchUseCase {
    suspend fun getCurrentWeatherUseCase(
        long: Double,
        lat: Double
    ): Flow<ResponseStatus<WeatherModel>>

    suspend fun getWeatherByCity(
        cityName: String
    ): WeatherModel?

    suspend fun insertWeather(
        withLocationWeatherEntity: WeatherModel,
        locationWithCityList: List<WeatherModel>
    )

    fun getAllCachedData(): Flow<List<WeatherEntity>>

    suspend fun clearAll()
}