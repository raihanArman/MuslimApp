package com.raydev.domain.usecase.prayer

import com.google.android.gms.maps.model.LatLng
import com.raydev.shared.model.PrayerTime
import com.raydev.domain.repository.PrayerRepository

class GetCurrentPrayerTimeUseCase(
    private val repository: PrayerRepository
) {
//    operator fun invoke(latLng: LatLng): PrayerTime {
//        return repository.getCurrentPrayerTime()
//    }
}