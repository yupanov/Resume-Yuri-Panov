package com.github.yupanov.resumeyp


import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.yupanov.resumeyp.weather.Weather
import com.github.yupanov.resumeyp.weather.database.WeatherDao
import com.github.yupanov.resumeyp.weather.database.WeatherDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class DbTest {

    private lateinit var dao: WeatherDao
    private lateinit var db: WeatherDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        dao = db.dao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWeather() = runBlocking {
        val weather = Weather(System.currentTimeMillis(),"location", "descr", 0F, 0F, 0F)
        dao.insertWeather(weather)
        val weatherHistory = dao.selectWeatherByTime()
        val listWeather = weatherHistory.value
        val first = listWeather?.get(0)
        assertEquals("descr", first?.description )
    }
}
