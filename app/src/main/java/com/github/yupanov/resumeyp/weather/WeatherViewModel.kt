package com.github.yupanov.resumeyp.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {
    private val id = "3433955"

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    private val _res = MutableLiveData<String>()
    val res: LiveData<String>
        get() = _res


    init {
        getWeather(id)
    }

    private fun getWeather(id: String) {
        viewModelScope.launch {
            try {
                val weatherFromJson = WeatherApi.retrofitService.getWeather(id, KEY, UNITS, lang)
                val weatherShort = Weather(weatherFromJson.name, weatherFromJson.weather[0].description, weatherFromJson.main.temp, weatherFromJson.main.tempMin, weatherFromJson.main.tempMax)
                _weather.value = weatherShort
            } catch (e: Exception) {
                _weather.value = e.message?.let { Weather("BA", it, 24.5F, 22.5F, 26.5F) }
            }
        }
    }

//    private fun getWeather(id: String) {
//        WeatherApi.retrofitService.getWeather(id, KEY, UNITS).enqueue(object: Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                _res.value = response.body()
//            }//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                _res.value = t.message
//            }
//        })
//    }

}