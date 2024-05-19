package com.rctiplus.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.raydev.prayer.receiver.AlarmReceiver
import com.raydev.shared.util.checkAndRequestMultiplePermissions
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by inject()
    companion object {
        const val REQUEST_CODE = 123

        fun goToMain(activity: Activity, withFinishAction: Boolean = false) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
            if (withFinishAction) {
                activity.finish()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestMultiplePermissions(
            permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.POST_NOTIFICATIONS
            ),
            onPermissionsGranted = {
                viewModel.getLocation()
                AlarmReceiver.createChannel(this)
            },
            onPermissionsDenied = {
            },
        )
        setContent {
            MainScreen(viewModel)
        }
    }
}
