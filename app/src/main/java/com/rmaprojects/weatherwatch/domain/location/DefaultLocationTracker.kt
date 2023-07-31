package com.rmaprojects.weatherwatch.domain.location

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import androidx.core.content.ContextCompat
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine


class DefaultLocationTracker(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getCurrentLocation(): LocationTrackerCondition<Location?> {
        val isFineLocationPermitted = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val isCoarseLocationPermitted = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = application.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isGpsEnabled) {
            return LocationTrackerCondition.NoGps()
        }

        if (!(isFineLocationPermitted || isCoarseLocationPermitted)) {
            return LocationTrackerCondition.MissingPermission()
        }

        return suspendCancellableCoroutine { cont ->
            fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
                .apply {
                    if (isComplete) {
                        if (isSuccessful) {
                            cont.resume(LocationTrackerCondition.Success(result)) {}
                        } else {
                            cont.resume(LocationTrackerCondition.Error()) {}
                        }
                        return@suspendCancellableCoroutine
                    }
                    addOnSuccessListener {
                        cont.resume(LocationTrackerCondition.Success(result)) {}
                    }
                    addOnFailureListener {
                        cont.resume(LocationTrackerCondition.Error()) {}
                    }
                    addOnCanceledListener {
                        cont.cancel()
                    }
                }
//            fusedLocationProviderClient.lastLocation.apply {
//                if (isComplete) {

//                }
//
//            }
        }
    }
}