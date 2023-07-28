package com.rmaprojects.weatherwatch.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rmaprojects.weatherwatch.data.repository.WeatherWatchRepositoryImpl
import com.rmaprojects.weatherwatch.domain.location.DefaultLocationTracker
import com.rmaprojects.weatherwatch.domain.location.LocationTracker
import com.rmaprojects.weatherwatch.domain.repository.WeatherWatchRepository
import com.rmaprojects.weatherwatch.domain.usecases.WeatherWatchInteractor
import com.rmaprojects.weatherwatch.domain.usecases.WeatherWatchUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUseCase(
        repository: WeatherWatchRepository
    ): WeatherWatchUseCase {
        return WeatherWatchInteractor(
            repository
        )
    }

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(
        application: Application
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application)
    }

    @Provides
    @Singleton
    fun providesLocationTracker(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): LocationTracker {
        return DefaultLocationTracker(
            fusedLocationProviderClient,
            application
        )
    }
}