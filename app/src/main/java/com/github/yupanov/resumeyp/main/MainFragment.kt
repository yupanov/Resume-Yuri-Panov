package com.github.yupanov.resumeyp.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.github.yupanov.resumeyp.R
import com.github.yupanov.resumeyp.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val binding = MainFragmentBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel

        binding.ivSmallPhoto.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_mainFragment_to_largePhotoFragment)
        }
        binding.btWeather.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_mainFragment_to_weatherFragment)
        }

        val adapter = ButtonsRvAdapter()
        binding.rvButtons.adapter = adapter

        return binding.root
    }
}