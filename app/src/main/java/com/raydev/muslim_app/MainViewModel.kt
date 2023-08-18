package com.raydev.muslim_app

import com.google.android.gms.maps.model.LatLng
import com.raihanarman.location.LocationManager
import com.raydev.anabstract.base.BaseViewModel
import com.raydev.domain.repository.PrayerRepository
import com.raydev.domain.usecase.prayer.GetCurrentPrayerTimeUseCase
import com.raydev.navigation.AppNavigator
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
class MainViewModel(
    private val appNavigator: AppNavigator,
    private val repository: PrayerRepository
): BaseViewModel() {
    val navigationChannel = appNavigator.navigationChannel

    init {
        getLocation()
    }

    private fun getLocation() {
        launch {
            LocationManager.instance.apply {
                getLocationFlowEvent().collect { location ->
                    repository.setCurrentPrayerTime(LatLng(location.latitude, location.longitude))
                }
            }
        }
    }
}