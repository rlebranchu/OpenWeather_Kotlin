package com.example.openweather_kotlin.views

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openweather_kotlin.R
import com.example.openweather_kotlin.models.WeatherForecastItem
import com.example.openweather_kotlin.utils.ViewUtils
import com.example.openweather_kotlin.utils.utils_conversion
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

        // Set function of the EditText of city search
        searchCity.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                viewModel.fetchWeatherData(s.toString())
            }
        })

        // Get cityName of cache
        val cityNameCache = CACHE_GETTER.getString("cityName", "Angers")
        cityName.text = cityNameCache

        // Now, we have the city showed, we could call api to get its weather
        viewModel.fetchWeatherData(cityNameCache ?: "Angers")

        // Listener to detect all modification of weather data modification in view model
        trackWeatherModification()
    }

    private fun trackWeatherModification() {
        viewModel.weather_data.observe(this) { weatherData ->
            weatherData.let {
                // To refresh all widgets in home page
                // Set CityName Label
                cityName.text = weatherData.name
                // Set Temperature label
                temperature.text = weatherData.main.temp.toString() + " °C"
                // Set Weather Icon
                ViewUtils.setWeatherIconToImageView(weatherIcon, weatherData.weather[0].icon)
            }
        }

        viewModel.weather_forecast_data.observe(this) { weather_forecast_data ->
            // Filter to take only the weather at 3PM for each day
            val weatherDays = weather_forecast_data.list.filter {
                // "k" DateTimeFormat : clock-hour-of-day (01-24)
                utils_conversion.stringDateFormatter(it.dtTxt,"yyyy-MM-dd HH:mm:ss","k") == "15"
            } as ArrayList<WeatherForecastItem>
            weatherForecastRecycleView = findViewById<View>(R.id.weatherForecastRecycleView) as RecyclerView
            weatherForecastRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            // Define adapter to RecycleView for Weather forecast days
            weatherForecastRecycleView.adapter = WeatherForecastAdapter(weatherDays)
            {
                Toast.makeText(this,"You have selected the weather of the day : ${it.dtTxt}",Toast.LENGTH_SHORT).show()
            }

        }

        // TODO : viewModel for weather_load : to show loading

        // TODO : viewModel for weather_error : to show error
    }
}