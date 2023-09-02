package com.raydev.prayer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.raydev.domain.repository.PrayerRepository
import com.raydev.prayer.receiver.PrayerWidgetReceiver
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Raihan Arman
 * @date 02/09/23
 */
class PrayerWorker(
    private val context: Context,
    workParams: WorkerParameters,
) : CoroutineWorker(context, workParams), KoinComponent {
    private val prayerRepository: PrayerRepository by inject()
    override suspend fun doWork(): Result {
        return try {
            val prayerTime = prayerRepository.getPrayerTime()
            PrayerWidgetReceiver.updateWidget(prayerTime, context)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
