package com.github.mrbean355.android.weatherapp.service

import com.github.mrbean355.android.weatherapp.service.dto.ForecastResponse
import com.github.mrbean355.android.weatherapp.service.dto.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("weather?units=metric")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") appid: String,
    ): Response<WeatherResponse>

    @GET("forecast?units=metric")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") appid: String,
    ): Response<ForecastResponse>
}