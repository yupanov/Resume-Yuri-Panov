package com.github.yupanov.resumeyp.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.yupanov.resumeyp.R
import com.github.yupanov.resumeyp.databinding.FragmentWeatherBinding
import com.github.yupanov.resumeyp.weather.database.WeatherDatabase
import com.google.android.material.snackbar.Snackbar
import java.util.*


class WeatherFragment : Fragment() {

    private val LOCATION_PERMISSION_REQUEST_CODE = 100

    private val weatherViewModel: WeatherViewModel by lazy {
        val application = requireActivity().application
        val dataSource = WeatherDatabase.getInstance(application).dao

        val vmFactory = WeatherViewModelFactory(dataSource, application)
        ViewModelProvider(this, vmFactory).get(WeatherViewModel::class.java)
    }


    lateinit var binding: FragmentWeatherBinding

    // Menu:
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.weather_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_about_weather) {
            findNavController().navigate(R.id.action_weatherFragment_to_aboutWeatherFragment)
        }
        return super.onOptionsItemSelected(item)
    }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
//        val binding = DataBindingUtil.inflate<FragmentWeatherBinding>(inflater, R.layout.fragment_weather, container, false) // The same?
        binding = FragmentWeatherBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = weatherViewModel

        startLocation()
        startWeather()
        binding.apply {
            btSaveData.setOnClickListener {
                weatherViewModel.saveWeatherData()
            }
            btClearData.setOnClickListener {
                weatherViewModel.clearWeatherData()
            }
            val adapter = WeatherHistoryAdapter()
            weatherViewModel.weatherData?.observe(viewLifecycleOwner, {
                it?.let{ adapter.submitList(it) }
            })
            rvWeatherHistory.adapter = adapter

            btFilter.setOnClickListener {
                when(rgFilter.checkedRadioButtonId) {
                    rbFilterAll.id -> weatherViewModel.timeFrom = 0L
                    rbFilterDay.id -> weatherViewModel.timeFrom = System.currentTimeMillis() - 86400000L
                    rbFilterMinute.id -> weatherViewModel.timeFrom = System.currentTimeMillis() - 60000L
                }
                weatherViewModel.refreshWeatherHistory()

            }



        }

        return binding.root
    }


    private fun startLocation() {
        // Check if the Location permission has been granted
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates()
        } else {
            // Permission is missing and must be requested.
            requestLocationPermission()
        }
    }

    private fun startWeather() {
        weatherViewModel.weather.observe( viewLifecycleOwner, {
            binding.apply {
                tvLocation.text = it.locationName.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
                tvDescription.text = it.description.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
                tvCurrentTemp.text = getString(R.string.temp, "%.1f".format(it.temperature))
                tvMinTemp.text = getString(R.string.temp, "%.1f".format(it.min))
                tvMaxTemp.text = getString(R.string.temp, "%.1f".format(it.max))
            }
        })
    }

    private fun requestLocationPermission() {
        // Permission has not been granted and must be requested.
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            val snackbar = Snackbar.make(requireView(), R.string.location_access_required, Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction(R.string.ok) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
            }.show()
        } else {
            val snackbar = Snackbar.make(requireView(), R.string.location_permission_not_available, Snackbar.LENGTH_SHORT)
            snackbar.show()
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) { // use when(requestCode)... if there are multiple permissions
            // Request for location permission.
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start location.
                Toast.makeText(activity, R.string.location_permission_granted, Toast.LENGTH_SHORT).show()
                requestLocationUpdates()
            } else {
                // Permission request was denied.
                Toast.makeText(activity, R.string.location_permission_denied, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestLocationUpdates() {
        weatherViewModel.locationLiveData.observe(binding.lifecycleOwner!!, {
            it?.let {
                binding.tvLatitude.text = formatCoordinate(it.latitude)
                binding.tvLongitude.text = formatCoordinate(it.longitude)

                weatherViewModel.lat = it.latitude
                weatherViewModel.lon = it.longitude

                weatherViewModel.fetchWeather()
            }
        })
    }

    private fun formatCoordinate(s: String): String {
        val dotIndex = s.indexOf(".", 0)
        return s.subSequence(0, dotIndex + 4).toString()
    }
}