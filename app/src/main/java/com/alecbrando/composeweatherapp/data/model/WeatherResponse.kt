package com.alecbrando.composeweatherapp.data.model

data class WeatherResponse(
    val alerts: List<Alert>,
    val current: Current,
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val hourly: List<Hourly>,
    val timezone: String,
    val timezone_offset: Int
)