package com.github.yupanov.resumeyp.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.yupanov.resumeyp.R
import com.github.yupanov.resumeyp.databinding.FragmentWeatherBinding
import java.util.*


class WeatherFragment : Fragment() {

    private val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
//        val binding = DataBindingUtil.inflate<FragmentWeatherBinding>(inflater, R.layout.fragment_weather, container, false) // The same?
        val binding = FragmentWeatherBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = weatherViewModel
        weatherViewModel.weather.observe( viewLifecycleOwner, {
            binding.apply {
                tvLocation.text = it.location
                tvDescription.text = it.description
                tvCurrentTemp.text = getString(R.string.temp, "%.1f".format(it.temperature))
                tvMinTemp.text = getString(R.string.temp, "%.1f".format(it.min))
                tvMaxTemp.text = getString(R.string.temp, "%.1f".format(it.max))
            }
        })
        return binding.root
    }


}