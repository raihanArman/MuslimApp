package com.raydev.prayer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.raydev.domain.repository.PrayerRepository
import com.raydev.prayer.receiver.PrayerWidgetReceiver
import com.raydev.shared.model.checkPrayerTimeIsNotEmpty
import kotlinx.coroutines.delay
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
    companion object {
        private const val MAXIMUM_RETRIES = 15
    }

    override suspend fun doWork(): Result {
        if (runAttemptCount >= MAXIMUM_RETRIES) return Result.failure()

        return try {
            val prayerTime = prayerRepository.getPrayerTime()
            if (prayerTime.checkPrayerTimeIsNotEmpty()) {
                PrayerWidgetReceiver.updateWidget(prayerTime, context)
                Result.success()
            } else {
                delay(1000L)
                Result.retry()
            }
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
