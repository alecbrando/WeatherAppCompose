package com.alecbrando.composeweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.alecbrando.composeweatherapp.ui.theme.ComposeWeatherAppTheme

import androidx.compose.ui.graphics.*
import androidx.navigation.compose.rememberNavController
import com.alecbrando.composeweatherapp.BottomNavBar.BottomNavBar


class MainActivity : ComponentActivity() {
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
}
