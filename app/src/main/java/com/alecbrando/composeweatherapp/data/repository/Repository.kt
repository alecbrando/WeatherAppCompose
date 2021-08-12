package com.alecbrando.composeweatherapp.data.repository

import android.util.Log
import com.alecbrando.composeweatherapp.Util.Resource
import com.alecbrando.composeweatherapp.data.api.WeatherApi_Imp
import com.alecbrando.composeweatherapp.data.model.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(
    private val weatherApiImp: WeatherApi_Imp
) {
    suspend fun getCurrentLocationsWeather(
        lat: String,
        lon: String
    ): Flow<Resource<WeatherResponse>> = flow {

        val res = weatherApiImp.getCurrentLocationsWeather(lat, lon)
        try {
            if (res.isSuccessful && res.body() != null) {
                emit(Resource.Success(res.body()!!))
            } else {
                emit(Resource.Error(null, message = "Something went wrong"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(null, message = e.localizedMessage ?: "Something went wrong"))
        }
    }
}