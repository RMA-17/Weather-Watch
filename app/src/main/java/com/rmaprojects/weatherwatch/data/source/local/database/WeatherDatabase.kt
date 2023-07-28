package com.rmaprojects.weatherwatch.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rmaprojects.weatherwatch.data.source.local.entity.WeatherEntity

@Database(
    version = 1,
    entities = [WeatherEntity::class]
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}