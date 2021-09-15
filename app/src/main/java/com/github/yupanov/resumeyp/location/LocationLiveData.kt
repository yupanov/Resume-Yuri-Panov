package com.github.yupanov.resumeyp.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.android.gms.location.*

class LocationLiveData(context: Context): LiveData<LocationDetails>()  {

    private val client = LocationServices.getFusedLocationProviderClient(context)

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            locationResult ?: return

            for(location in locationResult.locations) {
                setLocationData(location)
                Log.i("LocationCallback", location.toString())
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        client.lastLocation.addOnSuccessListener {
            setLocationData(it)
        }
        client.requestLocationUpdates(locationRequest, locationCallback, null)
    }


    override fun onInactive() {
        super.onInactive()
        client.removeLocationUpdates(locationCallback)
    }


    private fun setLocationData(location: Location) {
        value = LocationDetails(location.longitude.toString(), location.latitude.toString())
    }


    companion object {
        val locationRequest = LocationRequest.create().apply {
            val ONE_MUNUTE = 60000L
            interval = ONE_MUNUTE
            fastestInterval = ONE_MUNUTE / 4
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
    }
}