package com.github.yupanov.resumeyp.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.github.yupanov.resumeyp.R
import com.github.yupanov.resumeyp.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    // Menu:
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_about) {
            findNavController().navigate(R.id.action_mainFragment_to_aboutFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel

        binding.ivSmallPhoto.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_mainFragment_to_largePhotoFragment)
        }
        binding.btWeather.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_mainFragment_to_weatherFragment)
        }

        val adapter = ButtonsRvAdapter(resources)
        binding.rvButtons.adapter = adapter

        return binding.root
    }
}