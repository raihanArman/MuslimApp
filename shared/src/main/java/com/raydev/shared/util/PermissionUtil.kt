package com.raydev.shared.util

import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
fun AppCompatActivity.checkAndRequestMultiplePermissions(
    permissions: Array<String>,
    onPermissionsGranted: () -> Unit,
    onPermissionsDenied: () -> Unit,
) {
    val areAllPermissionsGranted = permissions.all { permission ->
        PackageManager.PERMISSION_GRANTED ==
            ContextCompat.checkSelfPermission(this, permission)
    }

    val permissionRequesterLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissionsResult ->
        if (permissionsResult.all { it.value }) {
            onPermissionsGranted()
        } else {
            onPermissionsDenied()
        }
    }

    if (!areAllPermissionsGranted) {
        val permissionsToRequest = permissions.filter { permission ->
            shouldShowRequestPermissionRationale(permission) ||
                ContextCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED
        }
        permissionRequesterLauncher.launch(permissionsToRequest.toTypedArray())
    } else {
        onPermissionsGranted()
    }
}

@Composable
fun CheckAndRequestMultiplePermission(
    permissions: Array<String>,
    onPermissionsGranted: () -> Unit,
    onPermissionsDenied: () -> Unit
) {
    val activity = (LocalContext.current as? Activity)
    if (activity != null) {
        val areAllPermissionsGranted = remember(permissions) {
            permissions.all { permission ->
                PackageManager.PERMISSION_GRANTED ==
                    ContextCompat.checkSelfPermission(activity, permission)
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
                activity.shouldShowRequestPermissionRationale(permission) ||
                    ContextCompat.checkSelfPermission(activity, permission) !=
                    PackageManager.PERMISSION_GRANTED
            }
            permissionRequesterLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            onPermissionsGranted()
        }
    }
}
