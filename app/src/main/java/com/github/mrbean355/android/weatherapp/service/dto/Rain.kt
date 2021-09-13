package com.github.mrbean355.android.weatherapp.service.dto


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val h: Double
)