package com.example.openweather_kotlin.models


import com.google.gson.annotations.SerializedName

data class WeatherForecastDay(
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("pop")
    val pop: Int,
    @SerializedName("sys")
    val sys: SysForecastDay,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: WindForecastDay
)