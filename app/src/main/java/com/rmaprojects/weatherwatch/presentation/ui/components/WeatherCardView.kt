package com.rmaprojects.weatherwatch.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cyclone
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.compose.WeatherWatchTheme
import com.rmaprojects.weatherwatch.BuildConfig
import com.rmaprojects.weatherwatch.util.Converters

@Composable
fun WeatherCardView(
    temperature: String?,
    modifier: Modifier = Modifier,
    mainCard: Boolean = false,
    todayDate: String = "",
    placeName: String = "",
    humidity: Int? = 0,
    windSpeed: String = "",
    weatherStatus: String = "",
    weatherIcon: String = ""
) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp,
            focusedElevation = 16.dp,
            hoveredElevation = 16.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (mainCard && temperature == null) {
                NoPermissionContent()
            }
            if (mainCard && temperature != null) {
                LargeCard(
                    temperature = "$temperature°C",
                    humidity = humidity ?: 0,
                    windSpeed = windSpeed,
                    placeName = placeName,
                    todayDate = todayDate,
                    weatherStatus = weatherStatus,
                    weatherIcon = weatherIcon
                )
            } else if (!mainCard && temperature != null) {
                SmallCard(
                    temperature = "$temperature°C",
                    humidity = humidity ?: 0,
                    windSpeed = windSpeed,
                    placeName = placeName,
                    weatherStatus = weatherStatus,
                    weatherIcon = weatherIcon
                )
            }
        }
    }
}

@Composable
fun NoPermissionContent() {
    Text(
        text = "Location access is not allowed",
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(24.dp))
    Text(
        text = "Please allow this app to use GPS for the accurate weather data",
        style = MaterialTheme.typography.titleSmall,
        textAlign = TextAlign.Center
    )
}

@Composable
fun LargeCard(
    temperature: String?,
    modifier: Modifier = Modifier,
    todayDate: String = "",
    humidity: Int = 0,
    windSpeed: String = "",
    placeName: String = "",
    weatherStatus: String = "",
    weatherIcon: String = "",
) {
    Column(
        modifier = modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = placeName,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = todayDate,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "${BuildConfig.WEATHER_ICON_URL}/$weatherIcon@4x.png",
                contentDescription = weatherStatus,
                modifier = Modifier
                    .size(156.dp)
            )
            Text(
                text = "$temperature",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = weatherStatus,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .weight(1.25F),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = Icons.Default.WaterDrop, contentDescription = null)
                Text(
                    text = "Humidity: ${Converters.humidityConverter(humidity)}",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier
                    .weight(1.25F),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = Icons.Default.Cyclone, contentDescription = null)
                Text(
                    text = "Wind Speed: $windSpeed",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun SmallCard(
    temperature: String?,
    modifier: Modifier = Modifier,
    humidity: Int = 0,
    windSpeed: String = "",
    placeName: String = "",
    weatherStatus: String = "",
    weatherIcon: String = "",
) {
    Row(
        modifier = modifier
            .height(128.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1F),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = "${BuildConfig.WEATHER_ICON_URL}/$weatherIcon@4x.png",
                    contentDescription = weatherStatus,
                    modifier = Modifier
                        .size(64.dp)
                )
                Text(
                    text = "$temperature",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .weight(3F),
        ) {
            Text(
                text = "$placeName: $weatherStatus",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(imageVector = Icons.Default.WaterDrop, contentDescription = null)
                Text(
                    text = "Humidity: ${Converters.humidityConverter(humidity)}"
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(imageVector = Icons.Default.Cyclone, contentDescription = null)
                Text(
                    text = "Wind Speed: $windSpeed"
                )
            }
        }
    }
}

@Preview
@Composable
fun WeatherCardViewPrev() {
    WeatherWatchTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            LazyColumn {
                items(10) {
                    WeatherCardView(
                        modifier = Modifier.padding(8.dp),
                        temperature = "31",
                        weatherIcon = "01d",
                        placeName = "London",
                        humidity = 45,
                        windSpeed = "45",
                        weatherStatus = "Clear",
                        todayDate = "Jum'at 28 Juni 2023"
                    )
                }
            }
        }
    }
}