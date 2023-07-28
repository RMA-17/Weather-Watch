package com.rmaprojects.weatherwatch.data.source.remote.network

import com.rmaprojects.weatherwatch.BuildConfig
import com.rmaprojects.weatherwatch.data.source.remote.response.GetWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") unit: String = "metric",
        @Query("appid") apiUrl: String = BuildConfig.WEATHER_API_KEY
    ): GetWeatherResponse


    @GET("weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("units") unit: String = "metric",
        @Query("appid") apiUrl: String = BuildConfig.WEATHER_API_KEY
    ): GetWeatherResponse
}