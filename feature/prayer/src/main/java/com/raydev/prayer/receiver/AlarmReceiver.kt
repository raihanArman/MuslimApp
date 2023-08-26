package com.raydev.prayer.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.raydev.prayer.NotificationAlarmConstants.CHANNEL_ID
import com.raydev.prayer.NotificationAlarmConstants.NOTIFICATION_ID
import com.raydev.prayer.NotificationAlarmConstants.NOTIFICATION_TITLE
import com.raydev.prayer.NotificationAlarmConstants.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
import com.raydev.prayer.NotificationAlarmConstants.VERBOSE_NOTIFICATION_CHANNEL_NAME
import com.raydev.prayer.R
import com.raydev.prayer.ReminderParams
import com.raydev.prayer.service.AlarmService


class AlarmReceiver: BroadcastReceiver() {
    private val TAG = "AlarmReceiver"
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: receive alarm")

        context?.startService(Intent(context, AlarmService::class.java))

        val message = intent?.getStringExtra(ReminderParams.KEY_MESSAGE)
        sendNotification(context, message)

    }

    fun sendNotification(context: Context?, message: String?){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
            val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            channel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            channel.enableVibration(true)
            channel.lockscreenVisibility = Notification.FLAG_FOREGROUND_SERVICE;

            // Add the channel
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }


        val intent = Intent(context, CancelServiceReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context,1,intent,PendingIntent.FLAG_MUTABLE)

        val answerAction: NotificationCompat.Action =
            NotificationCompat.Action.Builder(R.drawable.download, "Matikan", pendingIntent)
                .build()

        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.download)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(message ?: "Waktu shalat telah tiba!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setOngoing(true)
            .addAction(answerAction)
            .setAutoCancel(false)

        // Show the notification
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }

}