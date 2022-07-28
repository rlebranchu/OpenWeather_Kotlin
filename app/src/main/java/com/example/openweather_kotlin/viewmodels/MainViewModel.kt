package com.example.openweather_kotlin.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openweather_kotlin.models.WeatherForecastModel
import com.example.openweather_kotlin.models.WeatherModel
import com.example.openweather_kotlin.services.WeatherAPIService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel: ViewModel() {

    // We don't need to create instance of WeatherApiService because it's declared like Singleton
    // private val weatherApiService = WeatherAPIService;

    // TODO :: Set Comment
    private val disposable = CompositeDisposable()

    // Declaration of "State" of weather
    val weather_data = MutableLiveData<WeatherModel>()
    val weather_forecast_data = MutableLiveData<WeatherForecastModel>()
    val weather_error = MutableLiveData<Boolean>()
    val weather_loading = MutableLiveData<Boolean>()

    fun fetchWeatherData() {
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        weather_loading.value = true
        disposable.add(
            WeatherAPIService.getDataService()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // Manage the return of data of getDataService call
                .subscribeWith(object: DisposableSingleObserver<WeatherModel>(){
                    override fun onSuccess(value: WeatherModel) {
                        weather_data.value = value
                        // Inform of a good response
                        weather_error.value = false
                        //Stop loading status
                        weather_loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        // Inform of a bad response
                        weather_error.value = true
                        //Stop loading status
                        weather_loading.value = false
                    }
                })
        )
        disposable.add(
            WeatherAPIService.getWeatherForecastData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // Manage the return of data of getDataService call
                .subscribeWith(object: DisposableSingleObserver<WeatherForecastModel>(){
                    override fun onSuccess(value: WeatherForecastModel) {
                        weather_forecast_data.value = value
                        // Inform of a good response
                        weather_error.value = false
                        // Stop loading status
                        weather_loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        // Inform of a bad response
                        weather_error.value = true
                        // Stop loading status
                        weather_loading.value = false
                    }
                })
        )
    }


}