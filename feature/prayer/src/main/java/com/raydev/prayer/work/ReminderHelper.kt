package com.raydev.prayer.work

import android.content.Context
import androidx.work.*
import com.raydev.domain.repository.PrayerRepository
import com.raydev.domain.repository.QuranRepository
import com.raydev.prayer.ReminderParams
import com.raydev.shared.model.PrayerTime
import com.raydev.shared.model.RingType
import com.raydev.shared.model.checkPrayerTimeIsNotEmpty
import org.koin.java.KoinJavaComponent.inject

class ReminderHelper(
    context: Context
) {
    private val workManager: WorkManager = WorkManager.getInstance(context)
    private val repository: PrayerRepository by inject(PrayerRepository::class.java)

    private fun enableReminder(hours: Int, minutes: Int, reqCode: Int, enable: Boolean) {
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

    fun setupDefaultReminder() {
        while (true) {
            val prayerTime = repository.getPrayerTime()
            if (prayerTime.checkPrayerTimeIsNotEmpty()) {
                setAlarmSubuh(prayerTime, repository.getSubuhData().ringType == RingType.SOUND)
                setAlarmDhuhur(prayerTime, repository.getDhuhurData().ringType == RingType.SOUND)
                setAlarmIsya(prayerTime, repository.getIsyaData().ringType == RingType.SOUND)
                setAlarmMaghrib(prayerTime, repository.getMaghribData().ringType == RingType.SOUND)
                setAlarmAsr(prayerTime, repository.getAsharData().ringType == RingType.SOUND)
                break
            }
        }
    }

    fun setAlarmSubuh(prayerTime: PrayerTime, isRing: Boolean) {
        val subuh = prayerTime.fajr!!.split(":")
        enableReminder(
            hours = subuh[0].toTimeInteger(),
            minutes = subuh[1].toTimeInteger(),
            reqCode = 77,
            enable = isRing
        )
    }

    fun setAlarmDhuhur(prayerTime: PrayerTime, isRing: Boolean) {
        val dhuhur = prayerTime.dhuhr!!.split(":")
        enableReminder(
            hours = dhuhur[0].toTimeInteger(),
            minutes = dhuhur[1].toTimeInteger(),
            reqCode = 99,
            enable = isRing
        )
    }

    fun setAlarmAsr(prayerTime: PrayerTime, isRing: Boolean) {
        val asr = prayerTime.asr!!.split(":")
        enableReminder(
            hours = asr[0].toTimeInteger(),
            minutes = asr[1].toTimeInteger(),
            reqCode = 88,
            enable = isRing
        )
    }

    fun setAlarmMaghrib(prayerTime: PrayerTime, isRing: Boolean) {
        val maghrib = prayerTime.maghrib!!.split(":")
        enableReminder(
            hours = maghrib[0].toTimeInteger(),
            minutes = maghrib[1].toTimeInteger(),
            reqCode = 33,
            enable = isRing
        )
    }

    fun setAlarmIsya(prayerTime: PrayerTime, isRing: Boolean) {
        val isya = prayerTime.isya!!.split(":")
        enableReminder(
            hours = isya[0].toTimeInteger(),
            minutes = isya[1].toTimeInteger(),
            reqCode = 44,
            enable = isRing
        )
    }

}

fun String.toTimeInteger(): Int {
    return replaceFirst("0", "").toInt()
}