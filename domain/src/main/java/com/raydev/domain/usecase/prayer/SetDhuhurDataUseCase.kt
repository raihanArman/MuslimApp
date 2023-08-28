package com.raydev.domain.usecase.prayer

import com.raydev.domain.repository.PrayerRepository
import com.raydev.shared.model.PrayerData

class SetDhuhurDataUseCase(
    private val prayerRepository: PrayerRepository
) {
    operator fun invoke(prayerData: PrayerData) {
        prayerRepository.setDhuhurData(prayerData)
    }
}
