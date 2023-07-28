package com.rmaprojects.weatherwatch.presentation.views.home

import android.location.Location
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.weatherwatch.domain.location.LocationTracker
import com.rmaprojects.weatherwatch.domain.location.LocationTrackerCondition
import com.rmaprojects.weatherwatch.domain.model.WeatherModel
import com.rmaprojects.weatherwatch.domain.status.ResponseStatus
import com.rmaprojects.weatherwatch.domain.usecases.WeatherWatchUseCase
import com.rmaprojects.weatherwatch.presentation.views.home.states.HomeStates
import com.rmaprojects.weatherwatch.util.toWeatherEntity
import com.rmaprojects.weatherwatch.util.toWeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: WeatherWatchUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _currentLocation = MutableStateFlow<Location?>(null)
    private val _weatherData = mutableStateOf<WeatherModel?>(null)
    val weatherData: State<WeatherModel?> = _weatherData

    private val observeCityList = listOf(
        "New York",
        "Singapore",
        "Mumbai",
        "Delhi",
        "Sydney",
        "Melbourne, AU"
    )

    val weatherFromCityList = mutableStateListOf<WeatherModel?>()

    private val _homeScreenState = MutableStateFlow<HomeStates>(HomeStates.Loading)
    val homeScreeState = _homeScreenState.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HomeStates.Loading
    )

    fun getLocation() {
        viewModelScope.launch {
            when (val locationCondition = locationTracker.getCurrentLocation()) {
                is LocationTrackerCondition.Error -> {
                    _currentLocation.emit(null)
                        _homeScreenState.emit(HomeStates.Error("Error when getting GPS location"))
                }

                is LocationTrackerCondition.MissingPermission -> {
                        _currentLocation.emit(null)
                        _homeScreenState.emit(HomeStates.Error("Please allow this app to access your location"))
                }

                is LocationTrackerCondition.NoGps -> {
                        _currentLocation.emit(null)
                        _homeScreenState.emit(HomeStates.Error("Please turn on your GPS"))
                }

                is LocationTrackerCondition.Success -> {
                    _currentLocation.emit(locationCondition.location)
                    getWeather()
                }
            }
        }
    }

    private fun getWeather() {
        viewModelScope.launch {
            _homeScreenState.emit(HomeStates.Loading)

            val longitude = _currentLocation.value?.longitude
            val latitude = _currentLocation.value?.latitude

            Log.d("LONG", longitude.toString())
            Log.d("LAT", latitude.toString())

            if (longitude != null && latitude != null) {
                val getWeatherStatus = useCase.getCurrentWeatherUseCase(
                    longitude,
                    latitude
                ).stateIn(viewModelScope).value

                getWeatherFromCity()

                when (getWeatherStatus) {
                    is ResponseStatus.Error -> {
                        if (checkSavedCache() != 0) {
                            loadFromCache()
                        } else {
                            _homeScreenState.emit(HomeStates.Error(getWeatherStatus.message))
                        }
                    }

                    is ResponseStatus.Success -> {
                        _homeScreenState.emit(HomeStates.Success)
                        _weatherData.value = getWeatherStatus.data
                        useCase.clearAll()
                        useCase.insertWeather(
                            getWeatherStatus.data,
                            weatherFromCityList.toList().filterNotNull()
                        )
                    }
                }
            }
        }
    }

    private fun getWeatherFromCity() {
        viewModelScope.launch {
            val weatherDataList = observeCityList.map { cityName ->
                async { useCase.getWeatherByCity(cityName) }
            }.awaitAll()

            weatherFromCityList.addAll(weatherDataList)
        }
    }

    suspend fun checkSavedCache(): Int {
        return useCase.getAllCachedData().stateIn(viewModelScope).value.size
    }

    fun loadFromCache() {
        viewModelScope.launch {
            useCase.getAllCachedData().collect { weatherEntityList ->
                _weatherData.value = weatherEntityList.find { it.isFromLocation }?.toWeatherModel()
                val weatherModelList =
                    weatherEntityList.filter { (!it.isFromLocation) }.map { it.toWeatherModel() }
                weatherFromCityList.addAll(weatherModelList)
                _homeScreenState.value = HomeStates.Success
            }
        }
    }
}