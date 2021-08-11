package com.alecbrando.composeweatherapp.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alecbrando.composeweatherapp.Util.Resource
import com.alecbrando.composeweatherapp.data.model.WeatherResponse
import com.alecbrando.composeweatherapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel
@Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _currentLocationWeather = MutableLiveData<Resource<WeatherResponse>>(Resource.Loading(null))
    val currentLocationWeather : LiveData<Resource<WeatherResponse>> get() = _currentLocationWeather

    fun getCurrentLocationsWeather(
        lat: String,
        lon: String
    ) = viewModelScope.launch {
        repository.getCurrentLocationsWeather(lat, lon).collect {
            when (it) {
                is Resource.Error -> {
                    _currentLocationWeather.value = Resource.Error(null, it.message.toString())
                }
                is Resource.Loading -> {
                    _currentLocationWeather.value = Resource.Loading(null)
                }
                is Resource.Success -> {
                    _currentLocationWeather.value = Resource.Success(it.data!!)
                }
            }
        }

    }
}