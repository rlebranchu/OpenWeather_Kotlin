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
    val weatherData = MutableLiveData<WeatherModel>()
    val weatherForecastData = MutableLiveData<WeatherForecastModel>()
    val weatherError = MutableLiveData<Boolean>()
    val weatherLoading = MutableLiveData<Boolean>()

    fun fetchWeatherData(city: String) {
        getDataFromAPI(city)
    }

    private fun getDataFromAPI(city : String) {
        weatherLoading.value = true
        disposable.add(
            WeatherAPIService.getDataService(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // Manage the return of data of getDataService call
                .subscribeWith(object: DisposableSingleObserver<WeatherModel>(){
                    override fun onSuccess(value: WeatherModel) {
                        weatherData.value = value
                        // Inform of a good response
                        weatherError.value = false
                        //Stop loading status
                        weatherLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        // Inform of a bad response
                        weatherError.value = true
                        //Stop loading status
                        weatherLoading.value = false
                    }
                })
        )
        disposable.add(
            WeatherAPIService.getWeatherForecastData(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // Manage the return of data of getDataService call
                .subscribeWith(object: DisposableSingleObserver<WeatherForecastModel>(){
                    override fun onSuccess(value: WeatherForecastModel) {
                        weatherForecastData.value = value
                        // Inform of a good response
                        weatherError.value = false
                        // Stop loading status
                        weatherLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        // Inform of a bad response
                        weatherError.value = true
                        // Stop loading status
                        weatherLoading.value = false
                    }
                })
        )
    }




}