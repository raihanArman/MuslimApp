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
import com.raydev.shared.util.checkPermissions
import java.util.*

class MainActivity: ComponentActivity() {
    companion object{
        const val REQUEST_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkPermissions(REQUEST_CODE)) {

        }
        setContent {
            MainScreen()
        }
    }

}