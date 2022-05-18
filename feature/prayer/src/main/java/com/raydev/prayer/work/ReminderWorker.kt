package com.raydev.prayer.work

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.raydev.prayer.ReminderParams
import com.raydev.prayer.service.AlarmReceiver
import java.util.*

class ReminderWorker(
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val hours = inputData.getInt(ReminderParams.KEY_HOURS, 0)
        val minutes = inputData.getInt(ReminderParams.KEY_MINUTE, 0)
        val reqCode = inputData.getInt(ReminderParams.KEY_REQUEST_CODE, 0)
        val enable = inputData.getBoolean(ReminderParams.KEY_ENABLE, false)

        if (enable)
            triggerAlarm(hours, minutes, reqCode)
        else
            stopAlarm(hours, minutes, reqCode)

        return Result.success()
    }

    fun triggerAlarm(hours: Int, minutes: Int, reqCode: Int){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)

        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarm.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                PendingIntent.getBroadcast(
                    context,
                    reqCode,
                    Intent(context, AlarmReceiver::class.java),
                    FLAG_UPDATE_CURRENT
                )
            )
        }else {
            alarm.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                PendingIntent.getBroadcast(
                    applicationContext,
                    reqCode,
                    Intent(applicationContext, AlarmReceiver::class.java),
                    FLAG_UPDATE_CURRENT
                )
            )
        }

    }

    fun stopAlarm(hours: Int, minutes: Int, reqCode: Int) {
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(
            PendingIntent.getBroadcast(
                applicationContext,
                reqCode,
                Intent(applicationContext, AlarmReceiver::class.java),
                FLAG_UPDATE_CURRENT
            ))

//        applicationContext.stopService(Intent(applicationContext, AlarmSoundService::class.java))
    }

}