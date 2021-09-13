package com.github.mrbean355.android.weatherapp.service

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Application
import android.location.Location
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.github.mrbean355.android.weatherapp.BuildConfig
import com.github.mrbean355.android.weatherapp.service.dto.Forecast
import com.github.mrbean355.android.weatherapp.service.dto.ForecastResponse
import com.github.mrbean355.android.weatherapp.service.dto.WeatherResponse
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val service: OpenWeatherService,
    private val application: Application
) {

    @RequiresPermission(ACCESS_FINE_LOCATION)
    fun currentLocationWeather(): Flow<WeatherResponse?> {
        return locationFlow().map {
            service.getCurrentWeather(it.latitude, it.longitude, BuildConfig.API_KEY)
                .body()
        }
    }

    @RequiresPermission(ACCESS_FINE_LOCATION)
    fun weatherForecast(): Flow<List<Forecast>> {
        return locationFlow().map {
            service.getWeatherForecast(it.latitude, it.longitude, BuildConfig.API_KEY)
                .body()
        }.filterNotNull().map {
            extractDailyForecast(it)
        }
    }

    @RequiresPermission(ACCESS_FINE_LOCATION)
    private fun locationFlow() = channelFlow<Location> {
        val client = LocationServices.getFusedLocationProviderClient(application)
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                trySend(result.lastLocation)
            }
        }
        val request = LocationRequest.create()
            .setInterval(10_000)
            .setFastestInterval(5_000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setSmallestDisplacement(170f)

        client.requestLocationUpdates(request, callback, Looper.getMainLooper())

        awaitClose {
            client.removeLocationUpdates(callback)
        }
    }

    private fun extractDailyForecast(response: ForecastResponse): List<Forecast> {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val future = response.list.filter {
            val cal = Calendar.getInstance().apply {
                time = Date(it.dt * 1_000)
            }
            cal.get(Calendar.DAY_OF_MONTH) > today
        }

        val dailyForecast = future.groupBy {
            Calendar.getInstance().apply {
                time = Date(it.dt * 1_000)
            }.get(Calendar.DAY_OF_MONTH)
        }

        return dailyForecast.mapValues { (_, forecast) ->
            forecast.getOrNull(4)
        }.mapNotNull { (_, forecast) ->
            forecast
        }
    }
}