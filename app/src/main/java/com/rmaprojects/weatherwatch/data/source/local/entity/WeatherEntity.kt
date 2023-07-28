package com.rmaprojects.weatherwatch.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_weather")
data class WeatherEntity(
    val isFromLocation: Boolean,
    val longitude: Double,
    val latitude: Double,
    val weatherStatus: String,
    val weatherIconUrl: String,
    val temperature: Double,
    val humidity: Int,
    val windSpeed: Double,
    val placeName: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)