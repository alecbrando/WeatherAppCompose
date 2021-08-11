package com.alecbrando.composeweatherapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alecbrando.composeweatherapp.Home.HomeScreen


@Composable
fun Navigation(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = "home_route"){
        composable(route = "home_route"){
            HomeScreen()
        }
    }
}