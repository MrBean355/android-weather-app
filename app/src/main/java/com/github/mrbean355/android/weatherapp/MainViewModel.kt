package com.github.mrbean355.android.weatherapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.github.mrbean355.android.weatherapp.service.WeatherRepository
import com.github.mrbean355.android.weatherapp.service.dto.WeatherResponse
import kotlinx.coroutines.flow.Flow

@SuppressLint("MissingPermission")
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = WeatherRepository()

    val weather: Flow<WeatherResponse?> = repo.currentLocationWeather(getApplication() as Context)

}