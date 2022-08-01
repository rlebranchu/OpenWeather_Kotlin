package com.example.openweather_kotlin.services

import com.example.openweather_kotlin.models.WeatherForecastModel
import com.example.openweather_kotlin.models.WeatherModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherAPI {

    @GET("data/2.5/weather?appid=0022c8bd76c14326141d34801d80aa8c&units=metric")
    fun getWeatherData(@Query("q") city: String): Single<WeatherModel>

    @GET("data/2.5/forecast?appid=0022c8bd76c14326141d34801d80aa8c&units=metric")
    fun getWeatherForecastData(@Query("q") city: String): Single<WeatherForecastModel>

}