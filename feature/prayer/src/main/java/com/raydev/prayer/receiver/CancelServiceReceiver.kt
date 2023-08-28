package com.raydev.prayer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.raydev.prayer.NotificationAlarmConstants
import com.raydev.prayer.service.AlarmService

class CancelServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.stopService(Intent(context, AlarmService::class.java))
        NotificationManagerCompat.from(context!!).cancel(NotificationAlarmConstants.NOTIFICATION_ID)
    }
}
