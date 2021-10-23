package com.github.yupanov.resumeyp.weather

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.yupanov.resumeyp.R

@BindingAdapter("weather_status")
fun bindWeatherStatus(statusImageView: ImageView, weatherStatus: WeatherStatus) {
    when(weatherStatus) {
        WeatherStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        WeatherStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        WeatherStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
