package com.rmaprojects.weatherwatch.di

import android.app.Application
import androidx.room.Room
import com.rmaprojects.weatherwatch.data.source.local.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): WeatherDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            WeatherDatabase::class.java,
            "weatherwatch.db"
        ).build()
    }
}