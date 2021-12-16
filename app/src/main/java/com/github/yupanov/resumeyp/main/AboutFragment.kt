package com.github.yupanov.resumeyp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.github.yupanov.resumeyp.R
import com.github.yupanov.resumeyp.databinding.FragmentAboutBinding
import com.github.yupanov.resumeyp.isWeatherAboutShown

class AboutFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAboutBinding.inflate(inflater)
        binding.btAboutGoToWeather.setOnClickListener {
            if (isWeatherAboutShown) {
                findNavController().navigate(R.id.action_aboutFragment_to_weatherFragment)
            } else {
                isWeatherAboutShown = true
                findNavController().navigate(R.id.action_aboutFragment_to_aboutWeatherFragment)
            }
        }
        binding.btAboutOk.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
}