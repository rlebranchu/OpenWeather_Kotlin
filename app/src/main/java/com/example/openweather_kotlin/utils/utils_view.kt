package com.example.openweather_kotlin.utils

import android.widget.ImageView
import com.example.openweather_kotlin.R

object ViewUtils {
    fun setWeatherIconToImageView(imageView: ImageView, icon: String) {
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