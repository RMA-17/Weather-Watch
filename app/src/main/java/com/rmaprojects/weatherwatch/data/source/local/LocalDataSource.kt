package com.rmaprojects.weatherwatch.data.source.local

import com.rmaprojects.weatherwatch.data.source.local.database.WeatherDao
import com.rmaprojects.weatherwatch.data.source.local.database.WeatherDatabase
import com.rmaprojects.weatherwatch.data.source.local.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val database: WeatherDatabase
) {
    suspend fun insertWeather(withLocationWeatherEntity: WeatherEntity, locationWithCityList: List<WeatherEntity>) {
        return database.weatherDao().upsertAll(withLocationWeatherEntity, locationWithCityList)
    }

    fun getAllCachedData(): Flow<List<WeatherEntity>> {
        return database.weatherDao().getAllCachedData()
    }

    suspend fun clearAll() {
        return database.weatherDao().clearAll()
    }

}