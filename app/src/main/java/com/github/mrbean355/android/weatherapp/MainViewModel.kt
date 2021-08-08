package com.github.mrbean355.android.weatherapp

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.*

@SuppressLint("MissingPermission")
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var lastLocation: Location? = null

    fun onPermissionGranted() {
        val application: Application = getApplication()
        val client = LocationServices.getFusedLocationProviderClient(application)
        val request = LocationRequest.create()
            .setInterval(10_000)
            .setFastestInterval(5_000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setSmallestDisplacement(170f)

        client.requestLocationUpdates(request, object : LocationCallback() {

            override fun onLocationResult(result: LocationResult) {
                lastLocation = result.lastLocation
            }

            override fun onLocationAvailability(availability: LocationAvailability) = Unit

        }, Looper.getMainLooper())
    }
}