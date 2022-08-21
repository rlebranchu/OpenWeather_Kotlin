package com.example.openweather_kotlin.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import com.example.openweather_kotlin.views.WeatherForecastDetailsAdapter

class DetailsFragment : Fragment() {

    //private lateinit var viewModel: MainViewModel
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        val homeBtn = view.findViewById<Button>(R.id.homeBtn)
        homeBtn.setOnClickListener{
            findNavController().navigate(R.id.action_detailsFragment_to_mainFragment)
        }

        // Listener to detect all modification of weather data modification in view model
        trackWeatherModification(view)

        return view
    }

    private fun trackWeatherModification(view: View) {
        sharedViewModel.weatherForecastData.observe(this.viewLifecycleOwner) { weather_forecast_data ->
            // Filter to take only the weather at 3PM for each day
            val weatherDays = weather_forecast_data.list as ArrayList<WeatherForecastItem>
            val weatherForecastDetailsRV = view.findViewById<RecyclerView>(R.id.weatherForecastDetailsRecycleView)
            weatherForecastDetailsRV.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            // Define adapter to RecycleView for Weather forecast days
            weatherForecastDetailsRV.adapter = WeatherForecastDetailsAdapter(weatherDays)
            {
                Toast.makeText(this.context,"You have selected the weather of the day : ${it.dtTxt}", Toast.LENGTH_SHORT).show()
            }

        }
    }

}