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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    override fun onStart() {
        super.onStart()

    }
}

