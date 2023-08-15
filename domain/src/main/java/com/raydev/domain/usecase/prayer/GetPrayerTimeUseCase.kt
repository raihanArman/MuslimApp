package com.raydev.domain.usecase.prayer

import com.google.android.gms.maps.model.LatLng
import com.raihanarman.prayer.PrayerTime
import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.repository.PrayerRepository
import com.raydev.domain.repository.QuranRepository
import com.raydev.shared.model.Surah
import kotlinx.coroutines.flow.Flow

class GetPrayerTimeUseCase(
    private val repository: PrayerRepository
) {
    operator fun invoke(latLng: LatLng): PrayerTime {
        return repository.getCurrentPrayerTime(latLng)
    }
}