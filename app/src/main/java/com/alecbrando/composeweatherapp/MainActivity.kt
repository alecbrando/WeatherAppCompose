package com.alecbrando.composeweatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.alecbrando.composeweatherapp.ui.theme.ComposeWeatherAppTheme

import androidx.compose.ui.graphics.*
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.alecbrando.composeweatherapp.BottomNavBar.BottomNavBar
import com.alecbrando.composeweatherapp.Home.HomeScreenViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel : HomeScreenViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
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
                    viewModel.getCurrentLocationsWeather(it.latitude.toString(), it.longitude.toString())
                }
            }
        setContent {
            val navHostController = rememberNavController()
            ComposeWeatherAppTheme {
               Scaffold(
                   bottomBar = { BottomNavBar(navHostController)}
               ) {
                   Navigation(navHostController)
               }
            }
        }
    }
}
