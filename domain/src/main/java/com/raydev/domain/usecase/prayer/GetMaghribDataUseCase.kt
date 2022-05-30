package com.raydev.domain.usecase.prayer

import com.raydev.domain.repository.PrayerRepository
import com.raydev.shared.model.PrayerData

class GetMaghribDataUseCase(
    private val repository: PrayerRepository
) {
    operator fun invoke(): PrayerData = repository.getMaghribData()
}