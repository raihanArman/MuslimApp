package com.raihanarman.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LastLocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.lang.IllegalStateException
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

    private val locationRequest = LocationRequest.create().apply {
        priority = Priority.PRIORITY_HIGH_ACCURACY
        interval = 1000L
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

    @SuppressLint("MissingPermission")
    fun getLocationFlowEvent(): Flow<Location> {
        val callbackFlow = callbackFlow<Location> {
            val locationCallback = object : LocationCallback(){
                override fun onLocationResult(result: LocationResult) {
                    for (location in result.locations){
                        trySend(location)
                    }
                }
            }

            fusedLocationProvide.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            ).addOnCanceledListener {
                cancel("Canceled by user")
            }.addOnFailureListener {
                cancel("Get location failure", it)
            }

            awaitClose {
                fusedLocationProvide.removeLocationUpdates(locationCallback)
            }
        }

        return callbackFlow.distinctUntilChanged { old, new ->
            old.distanceTo(new) < 10f
        }
    }



    fun getAddressLocation(currentLocation: Location, address: (Address?) -> Unit) {
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

    fun getAddressLocation(currentLocation: LatLng, address: (Address?) -> Unit) {
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