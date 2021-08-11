package com.alecbrando.composeweatherapp.data.api

import com.alecbrando.composeweatherapp.Constants.Constants
import com.alecbrando.composeweatherapp.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("onecall")
    suspend fun getCurrentLocationsWeather(
        @Query("appid") api_key : String = Constants.API_KEY,
        @Query("units") units : String = "imperial",
        @Query("exclude") params : String = "minutely,pop",
        @Query("lat") lat : String,
        @Query("lon") lon : String,
    ) : Response<WeatherResponse>
}