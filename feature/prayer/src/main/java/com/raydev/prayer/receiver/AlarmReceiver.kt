package com.raydev.prayer.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
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

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "AlarmReceiver"

        fun createChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

                val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
                val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(CHANNEL_ID, name, importance)
                channel.description = description
                channel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
                channel.enableVibration(true)
                channel.lockscreenVisibility = Notification.FLAG_FOREGROUND_SERVICE

                notificationManager?.createNotificationChannel(channel)
            }
        }
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: receive alarm")

        context?.startService(Intent(context, AlarmService::class.java))

        val message = intent?.getStringExtra(ReminderParams.KEY_MESSAGE)
        sendNotification(context, message)
    }

    private fun sendNotification(context: Context?, message: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.let { createChannel(it) }
        }

        val answerIntent = Intent(context, CancelServiceReceiver::class.java)
        val answerPendingIntent = PendingIntent.getBroadcast(context, 1, answerIntent, PendingIntent.FLAG_MUTABLE)
        val answerAction: NotificationCompat.Action =
            NotificationCompat.Action.Builder(R.drawable.download, "Matikan", answerPendingIntent)
                .build()

        val nestedIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://muslimapp.com/prayer") // <-- Notice this
                )
            )
            getPendingIntent(2345, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }

        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.download)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(message ?: "Waktu shalat telah tiba!")
            .setContentIntent(nestedIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setOngoing(true)
            .addAction(answerAction)
            .setAutoCancel(false)

        // Show the notification
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }
}
