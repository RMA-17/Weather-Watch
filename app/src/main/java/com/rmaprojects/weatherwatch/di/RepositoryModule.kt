package com.rmaprojects.weatherwatch.di

import com.rmaprojects.weatherwatch.data.repository.WeatherWatchRepositoryImpl
import com.rmaprojects.weatherwatch.domain.repository.WeatherWatchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repositoryImpl: WeatherWatchRepositoryImpl): WeatherWatchRepository
}