package com.github.yupanov.resumeyp.weather.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.yupanov.resumeyp.weather.Weather

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: Weather)

    @Query("SELECT * FROM weather_table") // WHERE time BETWEEN :fromTime AND :toTime ORDER BY time DESC"
    fun selectWeatherByTime(): LiveData<List<Weather>> // fromTime: Long, toTime: Long

    @Query("DELETE FROM weather_table")
    suspend fun clear()
}