package com.alecbrando.composeweatherapp.data.model

data class WeatherResponse(
    val alerts: List<Alert>,
    val current: Current,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Int
)