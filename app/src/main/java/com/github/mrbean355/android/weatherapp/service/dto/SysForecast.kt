package com.github.mrbean355.android.weatherapp.service.dto


import com.google.gson.annotations.SerializedName

data class SysForecast(
    @SerializedName("pod")
    val pod: String
)