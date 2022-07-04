package com.example.openweather_kotlin.views

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.openweather_kotlin.R
import com.example.openweather_kotlin.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private lateinit var CACHE_GETTER: SharedPreferences
    private lateinit var CACHE_SETTER: SharedPreferences.Editor

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
                setWeatherIconToImageView(weatherIcon,weatherData.weather[0].icon)
            }
        }

        // TODO : viewModel for weather_load : to show loading

        // TODO : viewModel for weather_error : to show error
    }

    private fun setWeatherIconToImageView(imageView: ImageView, icon: String) {
        if(icon == "01d")
            imageView.setImageResource(R.drawable.wicon01d)
        else if(icon == "01n")
            imageView.setImageResource(R.drawable.wicon01n)
        else if(icon == "02d")
            imageView.setImageResource(R.drawable.wicon02d)
        else if(icon == "02n")
            imageView.setImageResource(R.drawable.wicon02n)
        else if(icon.startsWith("03"))
            imageView.setImageResource(R.drawable.wicon03)
        else if(icon.startsWith("04"))
            imageView.setImageResource(R.drawable.wicon04)
        else if(icon.startsWith("09"))
            imageView.setImageResource(R.drawable.wicon09)
        else if(icon == "10d")
            imageView.setImageResource(R.drawable.wicon10d)
        else if(icon == "10n")
            imageView.setImageResource(R.drawable.wicon10n)
        else if(icon.startsWith("11"))
            imageView.setImageResource(R.drawable.wicon11)
        else if(icon.startsWith("13"))
            imageView.setImageResource(R.drawable.wicon13)
        else if(icon.startsWith("50"))
            imageView.setImageResource(R.drawable.wicon50)
        else
            imageView.setImageResource(R.drawable.wiconerror)
    }
}