package com.github.yupanov.resumeyp.weather

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.github.yupanov.resumeyp.location.LocationLiveData
import com.github.yupanov.resumeyp.weather.database.WeatherDao
import kotlinx.coroutines.launch

class WeatherViewModel(private val dataSource: WeatherDao, application: Application): AndroidViewModel(application) {

    val locationLiveData = LocationLiveData(application)
    private var weatherShort: Weather? = null
    private val TAG = "WeatherViewModel"

//    private val id = "3433955" // Buenos Aires
    lateinit var lat: String
    lateinit var lon: String

    private val _status = MutableLiveData<WeatherStatus>()
    val status: LiveData<WeatherStatus>
        get() = _status

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    private var weatherData = dataSource.selectWeatherByTime() //0L, System.currentTimeMillis()

    val weatherDataToText = Transformations.map(weatherData) { weatherDataToText(it) }


    init {
        _status.value = WeatherStatus.LOADING
    }


    fun fetchWeather() {
        viewModelScope.launch {
            try {
                _status.value = WeatherStatus.LOADING
                val retrofitService = WeatherApi.retrofitService
                val weatherFromJson = retrofitService.getWeather(lat, lon, KEY, UNITS, lang)
                weatherShort = Weather(
                    System.currentTimeMillis(),
                    weatherFromJson.name,
                    weatherFromJson.weather[0].description,
                    weatherFromJson.main.temp,
                    weatherFromJson.main.tempMin,
                    weatherFromJson.main.tempMax)
                _weather.value = weatherShort!!
                _status.value = WeatherStatus.DONE
            } catch (e: Exception) {
                _status.value = WeatherStatus.ERROR
                _weather.value = e.message?.let { Weather(0L,"error", it, 0F, 0F, 0F) }
            }
        }
    }

    fun saveWeatherData() {
        viewModelScope.launch {
            weatherShort?.let {
                Log.i(TAG, it.description)
                dataSource.insert(it)
            }
        }
    }

    private fun weatherDataToText(listWeather: List<Weather>): String {
        var dataString = ""
        listWeather.forEach {
            val time = it.time
            dataString += time
            dataString += "\n"
        }
        return dataString
    }
}


enum class WeatherStatus { LOADING, ERROR, DONE }


class WeatherViewModelFactory(
    private val dataSource: WeatherDao,
    private val application: Application): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown class")
    }
}
