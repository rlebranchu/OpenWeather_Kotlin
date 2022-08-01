package com.example.openweather_kotlin.services

import com.example.openweather_kotlin.models.WeatherForecastModel
import com.example.openweather_kotlin.models.WeatherModel
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

// Use Object word to define WeatherAPIService like Singleton
object WeatherAPIService {

    // Beginning of url to call all api requests
    private val BASE_URL = "https://api.openweathermap.org/"

    // Create instance to Retrofit to send URL Request with default params (baseUrl, data converts, etc.)
    // This makes it easier to manage the sending and return of URL requests
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getDataService(city: String): Single<WeatherModel> {
        // Get Data of default City
        return api.getWeatherData(city)
    }

    fun getWeatherForecastData(city: String) : Single<WeatherForecastModel> {
        // Get Data of 5 day weather forecast
        return api.getWeatherForecastData(city)
    }
}