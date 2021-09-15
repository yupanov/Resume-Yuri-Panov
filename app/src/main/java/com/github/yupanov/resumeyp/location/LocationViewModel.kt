package com.github.yupanov.resumeyp.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    val locationLiveData = LocationLiveData(application)
//        get() = locationLiveData
//    internal fun getLocationLiveData() = locationLiveData
}