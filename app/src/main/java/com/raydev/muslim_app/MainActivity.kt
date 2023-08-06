package com.raydev.muslim_app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import com.raihanarman.splash.SplashScreen
//import com.google.android.gms.location.*
//import com.google.android.gms.tasks.CancellationToken
//import com.google.android.gms.tasks.CancellationTokenSource
//import com.google.android.gms.tasks.OnTokenCanceledListener
//import com.google.android.gms.tasks.Task
import com.raydev.anabstract.base.BaseActivity
import com.raydev.shared.deeplink.AppLink
import java.util.*

class MainActivity : ComponentActivity() {

    private var currentLocation: Location? = null
//    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    companion object{
        const val REQUEST_CODE = 123
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//        getLocationUpdates()

//        val intent = Intent(this, SplashActivity::class.java)
//        startActivity(intent)
        setContent {
            MainScreen()
        }
    }

    private fun checkPermission(): Boolean{
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_CODE
            )
            return false
        } else {
            return true
        }
    }


    private fun getLocationUpdates() {
        if(checkPermission()) {
//            fusedLocationProviderClient?.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
//                override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token
//
//                override fun isCancellationRequested() = false
//            })?.addOnSuccessListener { location: Location? ->
//                    if (location != null){
//                        currentLocation = location
//                        getCityLocation()
//                        gointToNextScreen()
//                    }
//                }

        }
    }

    private fun gointToNextScreen() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("${AppLink.PrayerFeature.PRAYER_LINK}"))
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocationUpdates()
            }
        }
    }

//    private fun getCityLocation() {
//        try{
//            val addresses: List<Address>
//            val geocoder: Geocoder = Geocoder(this, Locale.getDefault())
//            addresses = geocoder.getFromLocation(currentLocation!!.latitude, currentLocation!!.longitude, 1)
//            Util.currentLocation = addresses[0].subAdminArea
//        }catch (e: Exception){
//            Util.currentLocation = ""
//        }
//    }
}