package com.github.yupanov.resumeyp.weather


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.yupanov.resumeyp.databinding.RvWeatherHistoryHolderBinding
import java.text.SimpleDateFormat

class WeatherHistoryAdapter: ListAdapter<Weather, WeatherHistoryAdapter.WeatherHistoryHolder>(WeatherDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHistoryHolder {
        return WeatherHistoryHolder.getHolder(parent)
    }

    override fun onBindViewHolder(holder: WeatherHistoryHolder, position: Int) {
        val curData = getItem(position)
        holder.bind(curData)
    }


    class WeatherHistoryHolder private constructor(val binding: RvWeatherHistoryHolderBinding): RecyclerView.ViewHolder(binding.root) {

        companion object{
            fun getHolder(parent: ViewGroup): WeatherHistoryHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RvWeatherHistoryHolderBinding.inflate(inflater)
                return WeatherHistoryHolder(binding)
            }
        }

        fun bind(curData: Weather) {
            val timeText = SimpleDateFormat("EEEE dd MMM yyyy', 'HH:mm")
                .format(curData.time).toString()
            binding.apply {
                tvTime.text = timeText
                tvHistoryLocation.text = curData.locationName
                tvHistoryDescription.text = curData.description
                tvHistoryTemp.text = curData.temperature.toInt().toString() + "\u00B0C"
            }
        }
    }

}

class WeatherDiffCallback: DiffUtil.ItemCallback<Weather>() {
    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem == newItem
    }

}