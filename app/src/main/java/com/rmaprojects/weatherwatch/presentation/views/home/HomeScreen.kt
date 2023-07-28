package com.rmaprojects.weatherwatch.presentation.views.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.rmaprojects.weatherwatch.presentation.ui.components.WeatherCardView
import com.rmaprojects.weatherwatch.presentation.views.home.content.LocationPermissionNotGrantedContent
import com.rmaprojects.weatherwatch.presentation.views.home.states.HomeStates
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    if (!locationPermissions.allPermissionsGranted) {
        LaunchedEffect(true) {
            locationPermissions.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        if (locationPermissions.allPermissionsGranted) {
            viewModel.getLocation()
        }
    }

    val homeScreenState by viewModel.homeScreeState.collectAsStateWithLifecycle()
    val time = SimpleDateFormat(
        "EEEE, dd MMMM yyyy",
        Locale.getDefault()
    ).format(Date(System.currentTimeMillis()))

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PermissionsRequired(
            multiplePermissionsState = locationPermissions,
            permissionsNotGrantedContent = { LocationPermissionNotGrantedContent(locationPermissions) },
            permissionsNotAvailableContent = {
                LocationPermissionNotGrantedContent(
                    locationPermissions
                )
            }
        ) {
            when (homeScreenState) {
                is HomeStates.Error -> {
                    ElevatedCard(
                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 12.dp,
                            focusedElevation = 16.dp,
                            hoveredElevation = 16.dp
                        ),
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = (homeScreenState as HomeStates.Error).message,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = {
                                viewModel.getWeather()
                            }) {
                                Text(text = "Retry")
                            }
                        }
                    }
                }

                is HomeStates.Loading -> {
                    CircularProgressIndicator()
                }

                is HomeStates.Success -> {

                    val weatherData by remember { viewModel.weatherData }

                    WeatherCardView(
                        modifier = Modifier.padding(12.dp),
                        temperature = "${weatherData?.temperature}",
                        mainCard = true,
                        todayDate = time,
                        placeName = "${weatherData?.placeName}",
                        humidity = weatherData?.humidity,
                        weatherIcon = "${weatherData?.weatherIconUrl}",
                        weatherStatus = "${weatherData?.weatherStatus}",
                        windSpeed = "${weatherData?.windSpeed}"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
        ) {
            items(viewModel.weatherFromCityList) {

            }
        }
    }
}