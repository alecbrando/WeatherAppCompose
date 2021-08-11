package com.alecbrando.composeweatherapp.data.api

import com.alecbrando.composeweatherapp.data.model.WeatherResponse
import retrofit2.Response
import javax.inject.Inject

class WeatherApi_Imp @Inject constructor(
    private val weatherApi : WeatherApi
) {
    suspend fun getCurrentLocationsWeather(
        lat: String,
        lon: String
    ): Response<WeatherResponse> = weatherApi.getCurrentLocationsWeather(lat = lat, lon = lon)
}