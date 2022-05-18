package com.raydev.prayer.work

import androidx.work.*
import com.raydev.prayer.ReminderParams

class ReminderHelper(
    private val workManager: WorkManager
) {
    fun enableReminder(hours: Int, minutes: Int, reqCode: Int, enable: Boolean) {
        val data = Data.Builder()
            .putInt(ReminderParams.KEY_HOURS, hours)
            .putInt(ReminderParams.KEY_MINUTE, minutes)
            .putInt(ReminderParams.KEY_REQUEST_CODE, reqCode)
            .putBoolean(ReminderParams.KEY_ENABLE, enable)
            .build()

        val reminderWorker = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInputData(data)
            .build()

        workManager.enqueueUniqueWork(
            "oneReminderWork_${System.currentTimeMillis()}",
            ExistingWorkPolicy.KEEP,
            reminderWorker
        )

        workManager.enqueue(reminderWorker)
    }
}