package com.alecbrando.composeweatherapp.Home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alecbrando.composeweatherapp.Util.Resource
import com.alecbrando.composeweatherapp.data.model.WeatherResponse
import com.alecbrando.composeweatherapp.data.repository.Repository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel
@Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val application: Context,
) : ViewModel() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val _currentLocationWeather = MutableLiveData<Resource<WeatherResponse>>(Resource.Loading(null))
    val currentLocationWeather : LiveData<Resource<WeatherResponse>> get() = _currentLocationWeather

    private fun getCurrentLocationsWeather(
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

    fun getLocationData(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
        if (ActivityCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                location?.let {
                    Log.d("Loading", it.longitude.toString())
                    Log.d("Loading", it.latitude.toString())
                    getCurrentLocationsWeather(it.latitude.toString(), it.longitude.toString())
                }
            }
    }
}
