package com.example.openweather_kotlin.views.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openweather_kotlin.R
import com.example.openweather_kotlin.models.WeatherForecastItem
import com.example.openweather_kotlin.utils.ViewUtils
import com.example.openweather_kotlin.utils.utils_conversion
import com.example.openweather_kotlin.viewmodels.MainViewModel
import com.example.openweather_kotlin.views.WeatherForecastAdapter

class MainFragment : Fragment() {

    //private lateinit var viewModel: MainViewModel
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val moreDetailsBtn = view.findViewById<Button>(R.id.moreDetailsBtn)
        moreDetailsBtn.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_detailsFragment)
        }

        // Set function of the EditText of city search
        val searchCityEdText = view.findViewById<EditText>(R.id.searchCity)
        searchCityEdText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                sharedViewModel.fetchWeatherData(s.toString())
            }
        })

        // Get cityName of cache
        val defaultCityName = "Angers"
        val cityNameLbl = view.findViewById<TextView>(R.id.cityName)
        cityNameLbl.text = defaultCityName

        // Now, we have the city showed, we could call api to get its weather
        sharedViewModel.fetchWeatherData(defaultCityName)

        // Listener to detect all modification of weather data modification in view model
        trackWeatherModification(view)

        return view
    }

    private fun trackWeatherModification(view: View) {
        sharedViewModel.weatherData.observe(this.viewLifecycleOwner) { weatherData ->
            weatherData.let {
                // To refresh all widgets in home page
                // Set CityName Label
                val cityNameLbl = view.findViewById<TextView>(R.id.cityName)
                cityNameLbl.text = weatherData.name
                // Set Temperature label
                val temperatureLbl = view.findViewById<TextView>(R.id.temperature)
                temperatureLbl.text = weatherData.main.temp.toString() + " °C"
                // Set Weather Icon
                val weatherIcon = view.findViewById<ImageView>(R.id.weatherIcon)
                ViewUtils.setWeatherIconToImageView(weatherIcon, weatherData.weather[0].icon)
            }
        }

        sharedViewModel.weatherForecastData.observe(this.viewLifecycleOwner) { weather_forecast_data ->
            // Filter to take only the weather at 3PM for each day
            val weatherDays = weather_forecast_data.list.filter {
                // "k" DateTimeFormat : clock-hour-of-day (01-24)
                utils_conversion.stringDateFormatter(it.dtTxt,"yyyy-MM-dd HH:mm:ss","k") == "15"
            } as ArrayList<WeatherForecastItem>
            val weatherForecastRV = view.findViewById<RecyclerView>(R.id.weatherForecastRecycleView)
            weatherForecastRV.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            // Define adapter to RecycleView for Weather forecast days
            weatherForecastRV.adapter = WeatherForecastAdapter(weatherDays)
            {
                Toast.makeText(this.context,"You have selected the weather of the day : ${it.dtTxt}", Toast.LENGTH_SHORT).show()
            }

        }

        // TODO : viewModel for weather_load : to show loading

        // TODO : viewModel for weather_error : to show error
    }

}