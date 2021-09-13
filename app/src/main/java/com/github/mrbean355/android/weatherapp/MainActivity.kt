package com.github.mrbean355.android.weatherapp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.mrbean355.android.weatherapp.service.dto.Forecast
import com.github.mrbean355.android.weatherapp.service.dto.WeatherResponse
import com.github.mrbean355.android.weatherapp.ui.theme.CloudyBlue
import com.github.mrbean355.android.weatherapp.ui.theme.RainyGrey
import com.github.mrbean355.android.weatherapp.ui.theme.SunnyGreen
import com.github.mrbean355.android.weatherapp.ui.theme.WeatherAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val requestPermission = registerForActivityResult(RequestPermission()) {
        // TODO: fetch current location if granted
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                val weather by viewModel.weather.collectAsState(null)
                val forecast by viewModel.forecast.collectAsState(emptyList())
                weather?.let {
                    rememberSystemUiController().setStatusBarColor(it.backgroundColour())
                }

                Column(
                    Modifier
                        .fillMaxSize()
                        .background(weather?.backgroundColour() ?: Color.White)
                ) {
                    weather?.let {
                        WeatherSummary(weather = it)
                        TemperatureSummary(it)
                        Divider(color = Color.White)
                    }
                    FiveDayForecast(forecast)
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
}

@Composable
fun WeatherSummary(weather: WeatherResponse) {
    Box {
        Image(
            painter = painterResource(id = weather.background()),
            contentDescription = "Background",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )
        Column(
            Modifier
                .padding(top = 48.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = formatTemperature(weather.main.temp), fontSize = 48.sp, color = Color.White)
            Text(text = weather.weather.first().main, fontSize = 28.sp, color = Color.White)
            Text(text = weather.name, fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun TemperatureSummary(weather: WeatherResponse) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatTemperature(weather.main.tempMin),
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.min_temperature), color = Color.White)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatTemperature(weather.main.temp),
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.now_temperature), color = Color.White)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatTemperature(weather.main.tempMax),
                fontSize = 18.sp,
                color = Color.White
            )
            Text(text = stringResource(R.string.max_temperature), color = Color.White)
        }
    }
}

@Composable
fun FiveDayForecast(forecast: List<Forecast>) {
    LazyColumn {
        items(forecast) { dayForecast ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp)
            ) {
                Text(
                    text = SimpleDateFormat("EEEE").format(Date(dayForecast.dt * 1_000)),
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Image(
                    painter = painterResource(dayForecast.forecastIcon()),
                    contentDescription = "Forecast icon",
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                )
                Text(
                    text = formatTemperature(dayForecast.main.temp),
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    }
}

@Composable
private fun formatTemperature(temperature: Double): String {
    return stringResource(R.string.temperature_degrees, temperature.roundToInt())
}

@DrawableRes
private fun WeatherResponse.background(): Int {
    val conditions = weather.first().main
    return when {
        conditions.contains("cloud", ignoreCase = true) -> R.drawable.forest_cloudy
        conditions.contains("rain", ignoreCase = true) -> R.drawable.forest_rainy
        else -> R.drawable.forest_sunny
    }
}

private fun WeatherResponse.backgroundColour(): Color {
    val conditions = weather.first().main
    return when {
        conditions.contains("cloud", ignoreCase = true) -> CloudyBlue
        conditions.contains("rain", ignoreCase = true) -> RainyGrey
        else -> SunnyGreen
    }
}

private fun Forecast.forecastIcon(): Int {
    val conditions = weather.first().main
    return when {
        conditions.contains("cloud", ignoreCase = true) -> R.drawable.partlysunny
        conditions.contains("rain", ignoreCase = true) -> R.drawable.rain
        else -> R.drawable.clear
    }
}