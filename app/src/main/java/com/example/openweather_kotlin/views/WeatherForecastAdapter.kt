package com.example.openweather_kotlin.views

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.openweather_kotlin.R
import com.example.openweather_kotlin.models.WeatherForecastDay
import com.example.openweather_kotlin.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.weather_listview_item.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeatherForecastAdapter(val weatherList : ArrayList<WeatherForecastDay>,val listener: (WeatherForecastDay) -> Unit) : RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {

    // This method will create and display each element from our WeatherForecastDay model there, returning the "ViewHolder" class.
    // This class will convert our wetaher day data into elements of our recycleView.
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val listView = LayoutInflater.from(parent.context).inflate(R.layout.weather_listview_item,parent, false)
        return ViewHolder(listView)
    }

    // This function give us the size of the weather list
    override fun getItemCount(): Int = weatherList.size

    // This method will update our recycleView with the element at the position given in parameter.
    // In order to have to load in memory only the elements displayed on the screen.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("XXX","onBindViewHolder")
        holder.bind(weatherList[position],listener)
    }

    class ViewHolder(val weatherItem : View) : RecyclerView.ViewHolder(weatherItem)
    {
        fun bind(weather: WeatherForecastDay, listener: (WeatherForecastDay) -> Unit) = with(itemView)
        {
            // Format Date of day in dd/MM Format
            var dateBeforeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            var dateTargetFormatter = DateTimeFormatter.ofPattern("dd/MM")
            var formattedDate = LocalDate.parse(weather.dtTxt,dateBeforeFormat).format(dateTargetFormatter)
            itemView.dayName.text = formattedDate
            ViewUtils.setWeatherIconToImageView(itemView.dayWeatherIcon,weather.weather[0].icon)

            // Define the action launched at each click on item
            setOnClickListener { listener(weather) }
        }
    }
}