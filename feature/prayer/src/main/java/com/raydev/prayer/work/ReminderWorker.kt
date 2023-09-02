package com.raydev.prayer.work

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.raydev.prayer.ReminderParams
import com.raydev.prayer.receiver.AlarmReceiver
import java.util.Calendar

class ReminderWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    companion object {
        private const val TAG = "ReminderWorker"
    }
    override suspend fun doWork(): Result {
        val hours = inputData.getInt(ReminderParams.KEY_HOURS, 0)
        val minutes = inputData.getInt(ReminderParams.KEY_MINUTE, 0)
        val reqCode = inputData.getInt(ReminderParams.KEY_REQUEST_CODE, 0)
        val enable = inputData.getBoolean(ReminderParams.KEY_ENABLE, false)
        val message = inputData.getString(ReminderParams.KEY_MESSAGE)

        if (enable) {
            stopAlarm(reqCode)
            triggerAlarm(hours, minutes, reqCode, message)
        } else
            stopAlarm(reqCode)

        return Result.success()
    }

    private fun triggerAlarm(hours: Int, minutes: Int, reqCode: Int, message: String?) {
        Log.d(TAG, "triggerAlarm: $hours, $minutes, $message")
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)

        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Calendar.getInstance().after(calendar)) {
            // Move to tomorrow
            Log.d(TAG, "triggerAlarm: move tomorrow $message")
            calendar.add(Calendar.DATE, 1)
        }

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(ReminderParams.KEY_MESSAGE, message)

        alarm.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            PendingIntent.getBroadcast(
                context,
                reqCode,
                intent,
                FLAG_MUTABLE
            ),
        )
        Log.d(TAG, "triggerAlarm: set alarm")
    }

    private fun stopAlarm(reqCode: Int) {
        Log.d(TAG, "stopAlarm: $reqCode")
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(
            PendingIntent.getBroadcast(
                applicationContext,
                reqCode,
                Intent(applicationContext, AlarmReceiver::class.java),
                FLAG_MUTABLE
            )
        )
    }
}
