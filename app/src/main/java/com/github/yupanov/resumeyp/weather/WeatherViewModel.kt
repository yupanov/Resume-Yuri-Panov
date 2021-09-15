package com.github.yupanov.resumeyp.weather

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.yupanov.resumeyp.uicontrollers.MainActivity
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {
//    private val id = "3433955"
    lateinit var lat: String
    lateinit var lon: String

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    private val _res = MutableLiveData<String>()
    val res: LiveData<String>
        get() = _res


    init {
        setCoordinates()
        getWeather(lat, lon)
    }

    private fun setCoordinates() {
        ???
    }

    private fun getWeather(lat: String, lon: String) {
        viewModelScope.launch {
            try {
                val weatherFromJson = WeatherApi.retrofitService.getWeather(lat, lon, KEY, UNITS, lang)
                val weatherShort = Weather(weatherFromJson.name, weatherFromJson.weather[0].description, weatherFromJson.main.temp, weatherFromJson.main.tempMin, weatherFromJson.main.tempMax)
                _weather.value = weatherShort
            } catch (e: Exception) {
                _weather.value = e.message?.let { Weather("BA", it, 24.5F, 22.5F, 26.5F) }
            }
        }
    }


}