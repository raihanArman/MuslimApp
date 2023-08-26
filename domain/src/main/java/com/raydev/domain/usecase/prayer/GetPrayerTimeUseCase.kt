package com.raydev.domain.usecase.prayer

import com.raydev.shared.model.PrayerTime
import com.raydev.domain.repository.PrayerRepository

class GetPrayerTimeUseCase(
    private val repository: PrayerRepository
) {
    operator fun invoke(): PrayerTime {
        return repository.getPrayerTime()
    }
}