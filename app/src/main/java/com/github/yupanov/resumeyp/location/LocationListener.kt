package com.github.yupanov.resumeyp.location

import android.location.Location

class LocationListener {

}

interface LocationListenerInterface {
    fun onLocationChanged (loc: Location)
}