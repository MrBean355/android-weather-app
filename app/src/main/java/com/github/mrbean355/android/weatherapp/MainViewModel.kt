package com.github.mrbean355.android.weatherapp

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.github.mrbean355.android.weatherapp.service.WeatherRepository
import com.github.mrbean355.android.weatherapp.service.dto.Forecast
import com.github.mrbean355.android.weatherapp.service.dto.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class MainViewModel @Inject constructor(
    repository: WeatherRepository
) : ViewModel() {

    val weather: Flow<WeatherResponse?> = repository.currentLocationWeather()
    val forecast: Flow<List<Forecast>> = repository.weatherForecast()

}