package com.alecbrando.composeweatherapp.data.api

import com.alecbrando.composeweatherapp.data.model.WeatherResponse
import retrofit2.Response

class WeatherApi_Imp : WeatherApi {
    override suspend fun getCurrentLocationsWeather(
        api_key: String,
        units: String,
        params: String,
        lat: String,
        lon: String
    ): Response<WeatherResponse> {
        TODO("Not yet implemented")
    }
}