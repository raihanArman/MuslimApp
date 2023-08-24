package com.raydev.muslim_app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.raihanarman.splash.SplashScreen
//import com.google.android.gms.location.*
//import com.google.android.gms.tasks.CancellationToken
//import com.google.android.gms.tasks.CancellationTokenSource
//import com.google.android.gms.tasks.OnTokenCanceledListener
//import com.google.android.gms.tasks.Task
import com.raydev.anabstract.base.BaseActivity
import com.raydev.prayer.work.ReminderHelper
import com.raydev.shared.deeplink.AppLink
import com.raydev.shared.util.checkAndRequestPermission
import com.raydev.shared.util.checkPermissions
import org.koin.android.ext.android.inject
import java.util.*

class MainActivity: ComponentActivity() {
    val reminderHelper by inject<ReminderHelper>()
    companion object{
        const val REQUEST_CODE = 123
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            checkAndRequestPermission(
                permissions = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.POST_NOTIFICATIONS
                ),
                onPermissionsGranted = {

                },
                onPermissionsDenied = {

                },
            )

            MainScreen()
        }

        reminderHelper.setupDefaultReminder()

    }

}