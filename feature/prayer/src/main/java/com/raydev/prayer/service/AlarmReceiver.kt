package com.raydev.prayer.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.raydev.prayer.NotificationAlarmConstants.CHANNEL_ID
import com.raydev.prayer.NotificationAlarmConstants.NOTIFICATION_ID
import com.raydev.prayer.NotificationAlarmConstants.NOTIFICATION_TITLE
import com.raydev.prayer.NotificationAlarmConstants.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
import com.raydev.prayer.NotificationAlarmConstants.VERBOSE_NOTIFICATION_CHANNEL_NAME
import com.raydev.workmanager.R

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        sendNotification(context)
    }

    fun sendNotification(context: Context?){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
            val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            // Add the channel
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_app_icon)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText("Waktu shalat telah tiba!")
            .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        // Show the notification
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }

}