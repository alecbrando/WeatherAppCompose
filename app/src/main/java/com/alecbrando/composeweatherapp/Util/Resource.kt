package com.alecbrando.composeweatherapp.Util

sealed class Resource<out T : Any>(val data: T? = null, val message: String? = null){
    class Success<out T : Any>(data: T) : Resource<T>(data)
    class Error<out T : Any>(data : T? = null, message: String) : Resource<T>(data, message)
    class Loading<out T : Any>(data : T? = null, message: String? = null) : Resource<T>(data, message)
}