package com.github.yupanov.resumeyp.weather

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.github.yupanov.resumeyp.location.LocationLiveData
import com.github.yupanov.resumeyp.uicontrollers.MainActivity
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class WeatherViewModel(application :Application): AndroidViewModel(application) {

    val locationLiveData = LocationLiveData(application)

//    private val id = "3433955"
    lateinit var lat: String
    lateinit var lon: String

    private val _status = MutableLiveData<WeatherStatus>()
    val status: LiveData<WeatherStatus>
        get() = _status

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    private val _res = MutableLiveData<String>()
    val res: LiveData<String>
        get() = _res


    init {
        _status.value = WeatherStatus.LOADING
    }


    fun fetchWeather() {
        viewModelScope.launch {
            try {
                _status.value = WeatherStatus.LOADING
                val retrofitService = WeatherApi.retrofitService
                val weatherFromJson = retrofitService.getWeather(lat, lon, KEY, UNITS, lang)
                val weatherShort = Weather(
                    weatherFromJson.name,
                    weatherFromJson.weather[0].description,
                    weatherFromJson.main.temp,
                    weatherFromJson.main.tempMin,
                    weatherFromJson.main.tempMax)
                _weather.value = weatherShort
                _status.value = WeatherStatus.DONE
            } catch (e: Exception) {
                _status.value = WeatherStatus.ERROR
                _weather.value = e.message?.let { Weather("error", it, 0F, 0F, 0F) }
            }
        }
    }

}

enum class WeatherStatus { LOADING, ERROR, DONE }