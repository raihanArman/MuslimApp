package com.raydev.muslim_app

import com.google.android.gms.maps.model.LatLng
import com.raihanarman.location.LocationManager
import com.raydev.anabstract.base.BaseViewModel
import com.raydev.domain.repository.PrayerRepository
import com.raydev.navigation.AppNavigator
import com.raydev.prayer.work.PrayerWidgetHelper
import com.raydev.prayer.work.ReminderHelper
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
class MainViewModel(
    private val appNavigator: AppNavigator,
    private val repository: PrayerRepository,
    private val prayerHelper: PrayerWidgetHelper,
    private val reminderHelper: ReminderHelper
) : BaseViewModel() {
    val navigationChannel = appNavigator.navigationChannel

    fun getLocation() {
        launch {
            LocationManager.instance.apply {
                getLocationFlowEvent().collect { location ->
                    repository.setCurrentPrayerTime(LatLng(location.latitude, location.longitude))
                    reminderHelper.setupDefaultReminder()
                    prayerHelper.setPrayerWidget()
                }
            }
        }
    }
}
