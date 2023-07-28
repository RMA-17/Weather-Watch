package com.rmaprojects.weatherwatch.domain.usecases

import android.util.Log
import com.rmaprojects.weatherwatch.domain.status.ResponseStatus
import com.rmaprojects.weatherwatch.domain.model.WeatherModel
import com.rmaprojects.weatherwatch.domain.repository.WeatherWatchRepository
import com.rmaprojects.weatherwatch.util.Converters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherWatchInteractor @Inject constructor(
    private val repository: WeatherWatchRepository
): WeatherWatchUseCase {
    override suspend fun getCurrentWeatherUseCase(long: Double, lat: Double): Flow<ResponseStatus<WeatherModel>> = flow {
        try {
            val data = repository.getTodayWeather(long, lat)
            val weather = Converters.convertToWeatherModel(data)
            emit(ResponseStatus.Success(weather))
        } catch (e: Exception) {
            emit(ResponseStatus.Error(e.message ?: "Error Occurred"))
            Log.d("ERR_FETCH_WEATHER", e.toString())
        }
    }

    override suspend fun getWeatherByCity(cityName: String): WeatherModel {
        return runCatching {
            val data = repository.getWeatherByCity(cityName)
            Converters.convertToWeatherModel(data)
        }.getOrNull()
    }
}