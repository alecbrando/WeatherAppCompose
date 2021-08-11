package com.alecbrando.composeweatherapp.Home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.alecbrando.composeweatherapp.R
import com.alecbrando.composeweatherapp.Util.Resource
import com.alecbrando.composeweatherapp.data.model.WeatherResponse
import com.alecbrando.composeweatherapp.ui.theme.ComposeWeatherAppTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@Composable
fun LoadingScreen() {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}


@Composable
fun InitialHomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val state = viewModel.currentLocationWeather.observeAsState()
    when(state.value){
        is Resource.Error -> TODO()
        is Resource.Loading -> LoadingScreen()
        is Resource.Success -> HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            TopDaySection()
            WeatherDetailSection()
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            WeatherDayIcons()
            LineSection()
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            Text(
                text = "Today",
                style = MaterialTheme.typography.body1,
                color = Color(0xFF6D81A2),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .padding(vertical = 10.dp)
            ) {
                TodayWeatherTimesItems()
            }
            Spacer(Modifier.padding(vertical = 10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Text(
                        text = "Tuesday",
                        style = MaterialTheme.typography.body1,
                    )
                    Box() {
                        Image(
                            painter = painterResource(id = R.drawable.ic_dry_clean),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_cloud_dark),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .offset(x = (-3).dp, y = 5.dp)
                        )
                    }
                    Row()
                    {
                        Text(
                            text = "19°",
                            style = MaterialTheme.typography.body1,
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 20.dp))
                        Text(
                            text = "15°",
                            style = MaterialTheme.typography.body1,
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun TodayWeatherTimesItems() {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "10AM",
            style = MaterialTheme.typography.body2,
        )
        Box() {
            Image(
                painter = painterResource(id = R.drawable.ic_dry_clean),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_cloud_dark),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .offset(x = (-3).dp, y = 5.dp)
            )
        }
        Text(
            text = "19°",
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun LineSection() {
    BoxWithConstraints(
//        modifier = Modifier.fillMaxSize()
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        val mediumColoredPoint1 = Offset(-1f, height * 0.02f)
        val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.01f)
        val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.02f)

        val mediumColoredPath = Path().apply {
            moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
            cubicTo(
                mediumColoredPoint1.x,
                mediumColoredPoint1.y,
                mediumColoredPoint2.x,
                mediumColoredPoint2.y,
                mediumColoredPoint3.x,
                mediumColoredPoint3.y
            )
            cubicTo(
                width * 0.6f, height * 0.04f,
                width * 0.7f, height * 0.09f,
                width * 0.8f, height * 0.11f,
            )
            cubicTo(
                width * 0.9f, height * 0.13f,
                width * 1f, height * 0.14f,
                width * 1.1f, height * 0.14f,
            )
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }

        Canvas(modifier = Modifier.fillMaxWidth()) {
            drawPath(path = mediumColoredPath, color = Color(0xFF706D7C), style = Stroke(3f))
        }
    }
}

@Composable
private fun TopDaySection() {
    Row(
        modifier = Modifier.height(250.dp)
    ) {
        Column(
            modifier = Modifier
//                        .height(200.dp)
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(vertical = 15.dp))
            Text(
                text = "San Francisco",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Text(
                text = "18°",
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Button(
                onClick = { },
                shape = MaterialTheme.shapes.small,
                colors = buttonColors(MaterialTheme.colors.onSurface)
            ) {
                Text(text = "Cloudy")
            }
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_dry_clean),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .offset(x = 80.dp, y = (-30).dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_small_cloud),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .offset(x = (40).dp, y = (-60).dp),
                alpha = 0.96f
            )
            Image(
                painter = painterResource(id = R.drawable.ic_cloud_dark),
                contentDescription = null,
                modifier = Modifier
                    .size(180.dp)
                    .offset(x = (-50).dp, y = (0).dp),
                alpha = 0.96f
            )

        }
    }
}

@Composable
private fun WeatherDetailSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_water_drop),
            contentDescription = null,
            tint = Color(0xFF6D81A2),
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
        Text(
            text = "13%"
        )
        Spacer(modifier = Modifier.padding(horizontal = 20.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_atmospheric),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF6D81A2)
        )
        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
        Text(
            text = "0.533 mBar"
        )
        Spacer(modifier = Modifier.padding(horizontal = 20.dp))
        Icon(
            imageVector = Icons.Default.Air,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF6D81A2)
        )
        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
        Text(
            text = "9 km/h"
        )
    }
}

@Composable
private fun WeatherDayIcons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_bright_circle),
            contentDescription = "sun",
            modifier = Modifier.size(20.dp),
        )
        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
        Text(
            text = "07:00AM",
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.padding(horizontal = 80.dp))
        Text(
            text = "06:00PM",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.offset(x = 7.dp, y = 40.dp)
        )
        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_moon),
            contentDescription = "moon",
            modifier = Modifier
                .size(20.dp)
                .offset(x = 7.dp, y = 40.dp),
        )
    }
}


@Preview
@Composable
fun PreviewHomeScreenLight() {
    ComposeWeatherAppTheme(darkTheme = false) {
        HomeScreen()
    }
}


@Preview
@Composable
fun PreviewHomeScreenDark() {
    ComposeWeatherAppTheme(darkTheme = true) {
        HomeScreen()
    }
}
