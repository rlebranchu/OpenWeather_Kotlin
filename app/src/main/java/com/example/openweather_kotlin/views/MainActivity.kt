package com.example.openweather_kotlin.views

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openweather_kotlin.R
import com.example.openweather_kotlin.utils.ViewUtils
import com.example.openweather_kotlin.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var CACHE_GETTER: SharedPreferences
    private lateinit var CACHE_SETTER: SharedPreferences.Editor

    private lateinit var weatherForecastRecycleView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // Creation (if not exist already) of cache file with private mode (so only for this application)
        CACHE_GETTER = getSharedPreferences(packageName, MODE_PRIVATE)
        // Construction of setter based on cache created just before
        CACHE_SETTER = CACHE_GETTER.edit()

        // Initialization of viewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Get cityName of cache
        var cityNameCache = CACHE_GETTER.getString("cityName", "Angers")
        cityName.text = cityNameCache

        // Now, we have the city showed, we could call api to get its weather
        viewModel.fetchWeatherData()

        // Listener to detect all modification of weather data modification in view model
        trackWeatherModification()
    }

    private fun trackWeatherModification() {
        viewModel.weather_data.observe(this) { weatherData ->
            weatherData.let {
                // To refresh all widgets in home page
                // Set Temperature label
                temperature.text = weatherData.main.temp.toString() + " °C"
                // Set Weather Icon
                ViewUtils.setWeatherIconToImageView(weatherIcon, weatherData.weather[0].icon)
            }
        }

        viewModel.weather_forecast_data.observe(this) { weather_forecast_data ->
            weatherForecastRecycleView = findViewById<View>(R.id.weatherForecastRecycleView) as RecyclerView
            weatherForecastRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            weatherForecastRecycleView.adapter = WeatherForecastAdapter(weather_forecast_data.list)
            {
                Toast.makeText(this,"You have selected the weather of the day : ${it.dtTxt}",Toast.LENGTH_SHORT).show()
            }
        }

        // TODO : viewModel for weather_load : to show loading

        // TODO : viewModel for weather_error : to show error
    }
}