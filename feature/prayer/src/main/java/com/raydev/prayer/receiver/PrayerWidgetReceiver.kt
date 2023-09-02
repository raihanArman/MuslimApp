package com.raydev.prayer.receiver

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import com.raydev.domain.repository.PrayerRepository
import com.raydev.prayer.ui.components.PrayerWidget
import com.raydev.prayer.work.PrayerHelper
import com.raydev.shared.model.PrayerTime
import org.koin.java.KoinJavaComponent.inject

/**
 * @author Raihan Arman
 * @date 02/09/23
 */
class PrayerWidgetReceiver : GlanceAppWidgetReceiver() {
    private val prayerHelper: PrayerHelper by inject(PrayerHelper::class.java)
    private val prayerRepository: PrayerRepository by inject(PrayerRepository::class.java)
    override val glanceAppWidget: GlanceAppWidget = PrayerWidget()

    companion object {
        suspend fun updateWidget(prayerTime: PrayerTime, context: Context) {
            val glanceId = GlanceAppWidgetManager(context).getGlanceIds(PrayerWidget::class.java).last()
            updateAppWidgetState(context, glanceId) { prefs ->
                prefs[PrayerWidget.addressKeyPreference] = prayerTime.address ?: ""
                prefs[PrayerWidget.subuhKeyPreference] = prayerTime.fajr ?: ""
                prefs[PrayerWidget.dhuhurKeyPreference] = prayerTime.dhuhr ?: ""
                prefs[PrayerWidget.asharKeyPreference] = prayerTime.asr ?: ""
                prefs[PrayerWidget.maghribKeyPreference] = prayerTime.maghrib ?: ""
                prefs[PrayerWidget.isyaKeyPreference] = prayerTime.isya ?: ""
            }

            println("Ampass kuididid -> PrayerWidgetReceiver updateWidget")
            PrayerWidget().update(context, glanceId)
        }
    }
}
