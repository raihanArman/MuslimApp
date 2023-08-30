package com.raydev.domain.usecase.prayer

import com.raydev.domain.repository.PrayerRepository
import com.raydev.shared.model.PrayerTime

class GetPrayerTimeUseCase(
    private val repository: PrayerRepository
) {
    operator fun invoke(): PrayerTime {
        return repository.getPrayerTime()
    }
}
