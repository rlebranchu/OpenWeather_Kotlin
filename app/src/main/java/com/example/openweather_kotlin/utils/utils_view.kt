package com.example.openweather_kotlin.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.openweather_kotlin.R

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

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