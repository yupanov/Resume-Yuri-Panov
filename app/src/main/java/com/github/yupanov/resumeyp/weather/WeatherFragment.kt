package com.github.yupanov.resumeyp.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.github.yupanov.resumeyp.R
import com.github.yupanov.resumeyp.databinding.FragmentWeatherBinding
import com.google.android.material.snackbar.Snackbar


class WeatherFragment : Fragment() {

    private val LOCATION_PERMISSION_REQUEST_CODE = 100

    private val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }
//    private val locationViewModel: LocationViewModel by lazy {
//        ViewModelProvider(this).get(LocationViewModel::class.java)
//    }

    lateinit var binding: FragmentWeatherBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
//        val binding = DataBindingUtil.inflate<FragmentWeatherBinding>(inflater, R.layout.fragment_weather, container, false) // The same?
        binding = FragmentWeatherBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startLocation()
        startWeather()
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
                tvLocation.text = it.location
                tvDescription.text = it.description
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
//        binding.locationViewModel = locationViewModel
        binding.weatherViewModel = weatherViewModel
        weatherViewModel.locationLiveData.observe(binding.lifecycleOwner!!, {
            binding.tvLatitude.text = it.latitude
            binding.tvLongitude.text = it.longitude

//            weatherViewModel.lat = it.latitude
//            weatherViewModel.lon = it.longitude
        })
    }
}