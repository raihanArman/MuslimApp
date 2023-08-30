package com.raydev.muslim_app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.raydev.prayer.work.ReminderHelper
import com.raydev.shared.util.checkPermissions
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val reminderHelper by inject<ReminderHelper>()
    companion object {
        const val REQUEST_CODE = 123
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions(REQUEST_CODE)
        setContent {
            MainScreen()
        }

        reminderHelper.setupDefaultReminder()
    }
}
