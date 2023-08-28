package com.raydev.muslim_app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
// import com.google.android.gms.location.*
// import com.google.android.gms.tasks.CancellationToken
// import com.google.android.gms.tasks.CancellationTokenSource
// import com.google.android.gms.tasks.OnTokenCanceledListener
// import com.google.android.gms.tasks.Task
import com.raydev.prayer.work.ReminderHelper
import com.raydev.shared.util.checkPermissions
import org.koin.android.ext.android.inject
import java.util.*

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
