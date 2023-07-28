package com.rmaprojects.weatherwatch.util

import com.rmaprojects.weatherwatch.data.source.local.entity.WeatherEntity
import com.rmaprojects.weatherwatch.data.source.remote.response.GetWeatherResponse
import com.rmaprojects.weatherwatch.domain.model.WeatherModel

object Converters {
    fun humidityConverter(humidityValue: Int): String {
        return when {
            humidityValue <= 20 -> {
                "Dry"
            }
            humidityValue in 21..60 -> {
                "Normal"
            }
            humidityValue in 61..100 -> {
                "Wet"
            }
            else -> "$humidityValue"
        }
    }

    fun convertToWeatherModel(data: GetWeatherResponse): WeatherModel {
        return WeatherModel(
            data.coord.lon,
            data.coord.lat,
            data.weather[0].main,
            data.weather[0].icon,
            data.main.temp,
            data.main.humidity,
            data.wind.speed,
            data.name
        )
    }
}

fun WeatherModel.toWeatherEntity(isFromLocation: Boolean): WeatherEntity {
    return WeatherEntity(
        isFromLocation,
        this.longitude,
        this.latitude,
        this.weatherStatus,
        this.weatherIconUrl,
        this.temperature,
        this.humidity,
        this.windSpeed,
        this.placeName
    )
}

fun WeatherEntity.toWeatherModel(): WeatherModel {
    return WeatherModel(
        this.longitude,
        this.latitude,
        this.weatherStatus,
        this.weatherIconUrl,
        this.temperature,
        this.humidity,
        this.windSpeed,
        this.placeName
    )
}