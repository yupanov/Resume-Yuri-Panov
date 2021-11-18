package com.github.yupanov.resumeyp.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.github.yupanov.resumeyp.R
import com.github.yupanov.resumeyp.databinding.FragmentAboutWeatherBinding

class AboutWeatherFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAboutWeatherBinding.inflate(inflater)
        binding.btAboutWeatherOk.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_aboutWeatherFragment_to_weatherFragment)
        }
        return binding.root
    }
}