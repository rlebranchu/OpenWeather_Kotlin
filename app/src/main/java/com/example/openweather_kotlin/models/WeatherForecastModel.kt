package com.example.openweather_kotlin.models


import com.google.gson.annotations.SerializedName

data class WeatherForecastModel(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: ArrayList<WeatherForecastDay>,
    @SerializedName("message")
    val message: Int
)