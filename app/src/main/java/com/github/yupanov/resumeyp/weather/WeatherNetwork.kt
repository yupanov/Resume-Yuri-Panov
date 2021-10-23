package com.github.yupanov.resumeyp.weather

import com.github.yupanov.resumeyp.weather.fromjson.WeatherFromJson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
val KEY = "49ed19ec9ab199a10d8e2629b7b77c8a"
val UNITS = "metric"
var lang = Locale.getDefault().getDisplayLanguage()
private val moshi = Moshi.Builder() // JSON adapter for retrofit
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeather (
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") key: String,
        @Query("units") units: String,
        @Query("lang") language: String): WeatherFromJson
}

object WeatherApi {
    val retrofitService : WeatherApiService by lazy { retrofit.create(WeatherApiService::class.java) }
}