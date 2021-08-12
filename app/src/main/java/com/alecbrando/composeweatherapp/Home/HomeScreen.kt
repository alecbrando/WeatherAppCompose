package com.alecbrando.composeweatherapp.Home

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.location.Location
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
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
import com.alecbrando.composeweatherapp.Util.getDateTime
import com.alecbrando.composeweatherapp.data.model.Daily
import com.alecbrando.composeweatherapp.data.model.Hourly
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
    LaunchedEffect(key1 = true) {
        viewModel.getLocationData()
    }
    val state = viewModel.currentLocationWeather.observeAsState()
    when (state.value) {
        is Resource.Error -> {
        }
        is Resource.Loading -> LoadingScreen()
        is Resource.Success -> HomeScreen(state.value?.data!!)
    }
}

@Composable
fun HomeScreen(
    data: WeatherResponse
) {
    val city by remember {
        mutableStateOf(data.timezone.split("/")[1])
    }
    val temperature by remember {
        mutableStateOf(data.current.temp.toString().split(".")[0])
    }
    val sunrise by remember {
        mutableStateOf(getDateTime(data.current.sunrise.toString()))
    }
    val sunset by remember {
        mutableStateOf(getDateTime(data.current.sunset.toString()))
    }
    val mBar by remember {
        mutableStateOf(data.current.pressure.toString())
    }
    val humidity by remember {
        mutableStateOf(data.current.humidity.toString())
    }
    val wind by remember {
        mutableStateOf(data.current.wind_gust.toString())
    }
    val weatherType by remember {
        mutableStateOf(data.current.weather[0].main)
    }

    val hourly by remember {
        mutableStateOf(data.hourly)
    }

    val daily by remember {
        mutableStateOf(data.daily)
    }

    val dailyScrollState = rememberScrollState()
    val weekScrollState = rememberScrollState()

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            TopDaySection(city = city, temperature = temperature, weatherType = weatherType)
            WeatherDetailSection(
                mbar = mBar,
                humidity = humidity,
                wind = wind
            )
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            WeatherDayIcons(sunrise = sunrise ?: "", sunset = sunset ?: "")
            LineSection()
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            Text(
                text = "Today",
                style = MaterialTheme.typography.body1,
                color = Color(0xFF6D81A2),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            DailyWeatherItems(
                scrollState = dailyScrollState,
                items = hourly
            )
            Spacer(Modifier.padding(vertical = 10.dp))
            WeeksWeatherItems(
                items = daily,
                scrollState = weekScrollState
            )
        }
    }
}

@Composable
private fun DailyWeatherItems(
    scrollState: ScrollState,
    items: List<Hourly>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(vertical = 10.dp)
            .horizontalScroll(scrollState),

        ) {
        items.forEach { hourly ->
            val time = getDateTime(hourly.dt.toString()) ?: ""
            @DrawableRes val icon1: Int = R.drawable.ic_dry_clean
            @DrawableRes val icon2: Int = R.drawable.ic_cloud_dark
            val temperature = hourly.temp.toString().split(".")[0]
            TodayWeatherTimesItems(
                time = time,
                icon1 = icon1,
                icon2 = icon2,
                temperature = temperature
            )
        }
    }
}

@Composable
private fun WeeksWeatherItems(
    scrollState: ScrollState,
    items: List<Daily>
) {
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items.forEach { daily ->
                val day = daily
                val min = daily.temp.min.toString().split(".")[0]
                val max = daily.temp.max.toString().split(".")[0]
            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                WeeksItems(
                    day = "",
                    min = min,
                    max = max
                )
            }
        }
    }
}

@Composable
private fun WeeksItems(
    day: String,
    min: String,
    max: String
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
            text = "${min}°",
            style = MaterialTheme.typography.body1,
        )
        Spacer(modifier = Modifier.padding(horizontal = 20.dp))
        Text(
            text = "${max}°",
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
private fun TodayWeatherTimesItems(
    time: String,
    icon1: Int,
    icon2: Int,
    temperature: String
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "${time}AM",
            style = MaterialTheme.typography.body2,
        )
        Box(
            //add min height when there is for only when there is one icon
        ) {
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
            text = "${temperature}°",
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
private fun TopDaySection(
    temperature: String,
    city: String,
    weatherType: String
) {

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
                text = if (city.contains("_")) {
                    var reformattedCity = ""
                    city.split("_").forEach {
                        reformattedCity += "$it "
                    }
                    reformattedCity
                } else city,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Text(
                text = "$temperature°",
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Button(
                onClick = { },
                shape = MaterialTheme.shapes.small,
                colors = buttonColors(MaterialTheme.colors.onSurface)
            ) {
                Text(text = weatherType)
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
private fun WeatherDetailSection(
    mbar: String,
    humidity: String,
    wind: String
) {
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
            text = "${humidity}%"
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
            text = "$mbar mBar"
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
            text = "${wind} km/h"
        )
    }
}

@Composable
private fun WeatherDayIcons(
    sunrise: String,
    sunset: String
) {
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
            text = "${sunrise}AM",
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.padding(horizontal = 80.dp))
        Text(
            text = "${sunset}PM",
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

//@Preview
//@Composable
//fun PreviewHomeScreenLight() {
//    ComposeWeatherAppTheme(darkTheme = false) {
//        HomeScreen()
//    }
//}
//
//
//@Preview
//@Composable
//fun PreviewHomeScreenDark() {
//    ComposeWeatherAppTheme(darkTheme = true) {
//        HomeScreen()
//    }
//}
