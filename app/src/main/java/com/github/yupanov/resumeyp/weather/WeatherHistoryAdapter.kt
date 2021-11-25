package com.github.yupanov.resumeyp.weather


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.yupanov.resumeyp.databinding.RvWeatherHistoryHolderBinding
import java.text.SimpleDateFormat

class WeatherHistoryAdapter: RecyclerView.Adapter<WeatherHistoryAdapter.WeatherHistoryHolder>() {


    var weatherData: List<Weather>? = null
        set(value) {
            field = value
            notifyDataSetChanged() // DiffUtil?
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHistoryHolder {
        return WeatherHistoryHolder.getHolder(parent)
    }

    override fun onBindViewHolder(holder: WeatherHistoryHolder, position: Int) {
        val curData = weatherData?.get(position)
        holder.bind(curData)
    }

    override fun getItemCount(): Int{
        if (weatherData == null) return 0
        else return weatherData!!.size
    }

    class WeatherHistoryHolder private constructor(val binding: RvWeatherHistoryHolderBinding): RecyclerView.ViewHolder(binding.root) {
        companion object{
            fun getHolder(parent: ViewGroup): WeatherHistoryHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RvWeatherHistoryHolderBinding.inflate(inflater)
                return WeatherHistoryHolder(binding)
            }
        }

        fun bind(curData: Weather?) {
            val timeText = SimpleDateFormat("EEEE dd MMM yyyy', 'HH:mm")
                .format(curData?.time).toString()
            binding.apply {
                tvTime.text = timeText
                tvHistoryLocation.text = curData?.locationName
                tvHistoryDescription.text = curData?.description
                tvHistoryTemp.text = curData?.temperature?.toInt().toString() + "\u00B0C"
            }

        }
    }

}