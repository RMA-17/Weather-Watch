package com.rmaprojects.weatherwatch.presentation.views.home

import android.location.Location
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rmaprojects.weatherwatch.domain.location.LocationTracker
import com.rmaprojects.weatherwatch.domain.location.LocationTrackerCondition
import com.rmaprojects.weatherwatch.domain.model.WeatherModel
import com.rmaprojects.weatherwatch.domain.status.ResponseStatus
import com.rmaprojects.weatherwatch.domain.usecases.WeatherWatchUseCase
import com.rmaprojects.weatherwatch.presentation.views.home.states.HomeStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
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

    val observeCityList = listOf(
        "New York",
        "Singapore",
        "Mumbai",
        "Delhi",
        "Sydney",
        "Melbourne, AU"
    )
    private val _weatherFromCityList = mutableListOf<WeatherModel>()
    val weatherFromCityList = _weatherFromCityList.toList()

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

    fun getWeather() {
        viewModelScope.launch {
            _homeScreenState.emit(HomeStates.Loading)

            val longitude = _currentLocation.value?.longitude
            val latitude = _currentLocation.value?.latitude

            val getWeatherStatus = useCase.getCurrentWeatherUseCase(
                longitude!!,
                latitude!!
            ).stateIn(viewModelScope).value

            when (getWeatherStatus) {
                is ResponseStatus.Error -> {
                    _homeScreenState.emit(HomeStates.Error(getWeatherStatus.message))
                }

                is ResponseStatus.Success -> {
                    _homeScreenState.emit(HomeStates.Success)
                    _weatherData.value = getWeatherStatus.data
                }
            }
        }
    }

    fun getWeatherFromCity() {

    }
}