package com.example.openweather_kotlin.models


import com.google.gson.annotations.SerializedName

data class WindForecastDay(
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("gust")
    val gust: Double,
    @SerializedName("speed")
    val speed: Double
)