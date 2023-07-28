package com.rmaprojects.weatherwatch.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): LocationTrackerCondition<Location?>
}