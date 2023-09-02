package com.raydev.prayer.ui.components

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.wrapContentHeight
import androidx.glance.layout.wrapContentWidth
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.raihan.ui.widget.BaseWidget
import com.raydev.prayer.R

/**
 * @author Raihan Arman
 * @date 02/09/23
 */
class PrayerWidget : BaseWidget() {
    companion object {
        const val ADDRESS_KEY_WIDGET = "com.raydev.prayer.address_key"
        const val SUBUH_TIME_KEY_WIDGET = "com.raydev.prayer.subuh_key"
        const val DHUHUR_TIME_KEY_WIDGET = "com.raydev.prayer.dhuhur_key"
        const val ASHAR_TIME_KEY_WIDGET = "com.raydev.prayer.ashar_key"
        const val MAGHRIB_TIME_KEY_WIDGET = "com.raydev.prayer.maghrib_key"
        const val ISYA_TIME_KEY_WIDGET = "com.raydev.prayer.isya_key"
        val addressKeyPreference = stringPreferencesKey(ADDRESS_KEY_WIDGET)
        val subuhKeyPreference = stringPreferencesKey(SUBUH_TIME_KEY_WIDGET)
        val dhuhurKeyPreference = stringPreferencesKey(DHUHUR_TIME_KEY_WIDGET)
        val asharKeyPreference = stringPreferencesKey(ASHAR_TIME_KEY_WIDGET)
        val maghribKeyPreference = stringPreferencesKey(MAGHRIB_TIME_KEY_WIDGET)
        val isyaKeyPreference = stringPreferencesKey(ISYA_TIME_KEY_WIDGET)
    }
    @Composable
    override fun WidgetContent() {
        val prayerTimeState = currentState<Preferences>()
        val address = prayerTimeState[addressKeyPreference].orEmpty()
        val subuhTime = prayerTimeState[subuhKeyPreference].orEmpty()
        val dhuhurTime = prayerTimeState[dhuhurKeyPreference].orEmpty()
        val asharTime = prayerTimeState[asharKeyPreference].orEmpty()
        val maghribTime = prayerTimeState[maghribKeyPreference].orEmpty()
        val isyaTime = prayerTimeState[isyaKeyPreference].orEmpty()

        Column(
            modifier = GlanceModifier
                .wrapContentHeight()
                .wrapContentWidth()
                .background(ImageProvider(R.drawable.background_widget))
                .padding(10.dp)
                .clickable(actionRunCallback<ActionUpdate>())
        ) {
            Text(
                text = address,
                style = TextStyle(
                    color = ColorProvider(color = Color.White),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(modifier = GlanceModifier.height(10.dp))
            PrayerRow(title = "Subuh", value = subuhTime)
            PrayerRow(title = "Dhuhur", value = dhuhurTime)
            PrayerRow(title = "Ashar", value = asharTime)
            PrayerRow(title = "Maghrib", value = maghribTime)
            PrayerRow(title = "Isya", value = isyaTime)
        }
    }

    @Composable
    fun PrayerRow(
        modifier: GlanceModifier = GlanceModifier,
        title: String,
        value: String
    ) {
        Row(
            modifier = modifier.padding(bottom = 5.dp),
        ) {
            Text(
                text = "$title : ",
                style = TextStyle(
                    color = ColorProvider(color = Color.White),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = GlanceModifier
                    .defaultWeight(),
            )
            Text(
                text = value,
                style = TextStyle(
                    color = ColorProvider(color = Color.White),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                ),
            )
        }
    }

    inner class ActionUpdate : ActionCallback {
        override suspend fun onAction(
            context: Context,
            glanceId: GlanceId,
            parameters: ActionParameters
        ) {
        }
    }
}
