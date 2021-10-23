package com.github.yupanov.resumeyp.weather
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class Weather(
    @PrimaryKey(autoGenerate = false)
    val time: Long,
    val locationName: String,
    val description: String,
    val temperature: Float,
    val min: Float,
    val max: Float
)

