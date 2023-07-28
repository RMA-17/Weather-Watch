package com.rmaprojects.weatherwatch.data.repository

import com.rmaprojects.weatherwatch.data.source.local.LocalDataSource
import com.rmaprojects.weatherwatch.data.source.local.entity.WeatherEntity
import com.rmaprojects.weatherwatch.data.source.remote.RemoteDataSource
import com.rmaprojects.weatherwatch.data.source.remote.network.ApiService
import com.rmaprojects.weatherwatch.data.source.remote.response.GetWeatherResponse
import com.rmaprojects.weatherwatch.domain.repository.WeatherWatchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherWatchRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): WeatherWatchRepository {
    override suspend fun getTodayWeather(long: Double, lat: Double): GetWeatherResponse {
        return remoteDataSource.getWeatherToday(lat, long)
    }

    override suspend fun getWeatherByCity(cityName: String): GetWeatherResponse {
        return remoteDataSource.getWeatherByCity(cityName)
    }

    override suspend fun insertWeather(withLocationWeatherEntity: WeatherEntity, locationWithCityList: List<WeatherEntity>) {
        return localDataSource.insertWeather(withLocationWeatherEntity, locationWithCityList)
    }

    override fun getAllCachedData(): Flow<List<WeatherEntity>> {
        return localDataSource.getAllCachedData()
    }

    override suspend fun clearAll() {
        return localDataSource.clearAll()
    }
}