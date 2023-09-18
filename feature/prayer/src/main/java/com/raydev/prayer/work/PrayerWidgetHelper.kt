package com.raydev.prayer.work

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

/**
 * @author Raihan Arman
 * @date 02/09/23
 */
class PrayerWidgetHelper(
    context: Context
) {
    private val workManager: WorkManager = WorkManager.getInstance(context)
    fun setPrayerWidget() {
        val prayerWorker = OneTimeWorkRequestBuilder<PrayerWidgetWorker>()
            .build()

        workManager.enqueueUniqueWork(
            "onePrayerWork_${System.currentTimeMillis()}",
            ExistingWorkPolicy.KEEP,
            prayerWorker
        )

        workManager.enqueue(prayerWorker)
    }
}
