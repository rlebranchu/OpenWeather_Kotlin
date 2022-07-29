package com.example.openweather_kotlin.models


import com.google.gson.annotations.SerializedName

data class SysForecast(
    @SerializedName("pod")
    val pod: String
)