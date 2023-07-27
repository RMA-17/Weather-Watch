package com.rmaprojects.weatherwatch.data.source.remote.response

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)