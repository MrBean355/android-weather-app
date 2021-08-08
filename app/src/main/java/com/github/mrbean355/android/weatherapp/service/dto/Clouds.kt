package com.github.mrbean355.android.weatherapp.service.dto


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)