package com.rmaprojects.weatherwatch.presentation.views.home.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.rmaprojects.weatherwatch.presentation.ui.components.WeatherCardView

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionNotGrantedContent(
    requiredPermission: MultiplePermissionsState,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WeatherCardView(temperature = null, mainCard = true)
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { requiredPermission.launchMultiplePermissionRequest() }) {
            Text(text = "Enable Permission")
        }
    }
}
