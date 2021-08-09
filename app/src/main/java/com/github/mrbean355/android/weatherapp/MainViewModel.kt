package com.github.mrbean355.android.weatherapp

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.github.mrbean355.android.weatherapp.service.WeatherRepository
import com.github.mrbean355.android.weatherapp.service.dto.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    repository: WeatherRepository
) : AndroidViewModel(application) {

    val weather: Flow<WeatherResponse?> = repository.currentLocationWeather(getApplication())

}