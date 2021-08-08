package com.github.mrbean355.android.weatherapp.service

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.github.mrbean355.android.weatherapp.BuildConfig
import com.github.mrbean355.android.weatherapp.service.dto.WeatherResponse
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map

class WeatherRepository {
    private val service = OpenWeatherService()

    @RequiresPermission(ACCESS_FINE_LOCATION)
    fun currentLocationWeather(context: Context): Flow<WeatherResponse?> {
        return locationFlow(context).map {
            service.getCurrentWeather(it.latitude, it.longitude, BuildConfig.API_KEY)
                .body()
        }
    }

    @RequiresPermission(ACCESS_FINE_LOCATION)
    private fun locationFlow(context: Context) = channelFlow<Location> {
        val client = LocationServices.getFusedLocationProviderClient(context)
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
}