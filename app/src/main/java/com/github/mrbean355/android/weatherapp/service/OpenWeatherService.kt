package com.github.mrbean355.android.weatherapp.service

import com.github.mrbean355.android.weatherapp.service.dto.WeatherResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("weather?units=metric")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") appid: String,
    ): Response<WeatherResponse>
}

fun OpenWeatherService(): OpenWeatherService = Retrofit.Builder()
    .baseUrl("https://api.openweathermap.org/data/2.5/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create()