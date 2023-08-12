package com.raydev.shared

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LastLocationRequest
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Locale

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
class LocationManager(
    private val context: Context
) {
    companion object: KoinComponent {
        val instance: LocationManager by inject()
    }

    private val fusedLocationProvide: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val locationRequest = LocationRequest.create().apply {
        priority = Priority.PRIORITY_HIGH_ACCURACY
        interval = 1000
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(lastLocation: (Location) -> Unit) {
        val lastLocationRequest = LastLocationRequest.Builder()
            .build()

        fusedLocationProvide.getLastLocation(lastLocationRequest)
            .addOnFailureListener {
                it.printStackTrace()
            }
            .addOnSuccessListener {
                println("AAAAA -> $it")
                lastLocation.invoke(it)
            }
    }

    fun getCityLocation(currentLocation: Location, address: (Address?) -> Unit) {
        try{
            val geocoder = Geocoder(context, Locale.getDefault())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(currentLocation.latitude, currentLocation.longitude, 1) {
                    address(it.firstOrNull())
                }
                return
            }

            try {
                address(geocoder.getFromLocation(currentLocation.latitude, currentLocation.longitude, 1)?.firstOrNull())
            } catch(e: Exception) {
                address(null)
            }
        }catch (e: Exception){
            address(null)
        }
    }

}