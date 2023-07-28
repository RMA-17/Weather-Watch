package com.rmaprojects.weatherwatch.data.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.rmaprojects.weatherwatch.data.source.local.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Upsert
    suspend fun upsertAll(withLocationWeatherEntity: WeatherEntity, locationWithCityList: List<WeatherEntity>)

    @Query("SELECT * FROM tbl_weather")
    fun getAllCachedData(): Flow<List<WeatherEntity>>

    @Query("DELETE FROM tbl_weather")
    suspend fun clearAll()
}