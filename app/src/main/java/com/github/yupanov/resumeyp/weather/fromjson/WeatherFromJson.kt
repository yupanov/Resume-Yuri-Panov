package com.github.yupanov.resumeyp.weather.fromjson

import com.squareup.moshi.Json

data class WeatherFromJson (
    var weather: List<Weather>,
    var main: Main,
    var id: Int,
    var name: String
)

data class Main (
    var temp: Float,
    var pressure: Int,
    var humidity: Int,
    @Json (name = "temp_min")
    var tempMin: Float,
    @Json (name = "temp_max")
    var tempMax: Float
)

data class Weather (
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)
