package com.raydev.shared.util

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
fun ComponentActivity.checkPermissions(requestCode: Int?): Boolean {
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
            requestCode ?: 0
        )
        return false
    } else {
        return true
    }
}

@Composable
fun ComponentActivity.CheckAndRequestPermission(
    permissions: Array<String>,
    onPermissionsGranted: () -> Unit,
    onPermissionsDenied: () -> Unit,
    actionDialogPermission: (() -> Unit?)? = null
) {
    val areAllPermissionsGranted = remember(permissions) {
        permissions.all { permission ->
            PackageManager.PERMISSION_GRANTED ==
                    ContextCompat.checkSelfPermission(this, permission)
        }
    }

    val permissionRequesterLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissionsResult ->
            if (permissionsResult.all { it.value }) {
                onPermissionsGranted()
            } else {
                onPermissionsDenied()
            }
        }
    )

    if (!areAllPermissionsGranted) {
        val permissionsToRequest = permissions.filter { permission ->
            shouldShowRequestPermissionRationale(permission) ||
                    ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
        }
        permissionRequesterLauncher.launch(permissionsToRequest.toTypedArray())

    } else {
        onPermissionsGranted()
    }
}