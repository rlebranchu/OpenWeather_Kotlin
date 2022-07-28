package com.example.openweather_kotlin.models


import com.google.gson.annotations.SerializedName

data class SysForecastDay(
    @SerializedName("pod")
    val pod: String
)