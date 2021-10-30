package com.github.mrbean355.android.weatherapp.service.dto

import com.google.gson.annotations.SerializedName

data class FullWeather(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int,
    @SerializedName("daily")
    val daily: List<Daily>
) {

    data class Daily(
        @SerializedName("dt")
        val dt: Long,
        @SerializedName("sunrise")
        val sunrise: Int,
        @SerializedName("sunset")
        val sunset: Int,
        @SerializedName("moonrise")
        val moonrise: Int,
        @SerializedName("moonset")
        val moonset: Int,
        @SerializedName("moon_phase")
        val moonPhase: Double,
        @SerializedName("temp")
        val temp: Temp,
        @SerializedName("feels_like")
        val feelsLike: FeelsLike,
        @SerializedName("pressure")
        val pressure: Int,
        @SerializedName("humidity")
        val humidity: Int,
        @SerializedName("dew_point")
        val dewPoint: Double,
        @SerializedName("wind_speed")
        val windSpeed: Double,
        @SerializedName("wind_deg")
        val windDeg: Int,
        @SerializedName("wind_gust")
        val windGust: Double,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("clouds")
        val clouds: Int,
        @SerializedName("pop")
        val pop: Double,
        @SerializedName("uvi")
        val uvi: Double,
        @SerializedName("rain")
        val rain: Double
    ) {
        data class Temp(
            @SerializedName("day")
            val day: Double,
            @SerializedName("min")
            val min: Double,
            @SerializedName("max")
            val max: Double,
            @SerializedName("night")
            val night: Double,
            @SerializedName("eve")
            val eve: Double,
            @SerializedName("morn")
            val morn: Double
        )

        data class FeelsLike(
            @SerializedName("day")
            val day: Double,
            @SerializedName("night")
            val night: Double,
            @SerializedName("eve")
            val eve: Double,
            @SerializedName("morn")
            val morn: Double
        )

        data class Weather(
            @SerializedName("id")
            val id: Int,
            @SerializedName("main")
            val main: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("icon")
            val icon: String
        )
    }
}