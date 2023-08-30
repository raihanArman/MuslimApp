package com.raydev.domain.usecase.prayer

import com.raydev.domain.repository.PrayerRepository
import com.raydev.shared.model.PrayerData

class GetSubuhDataUseCase(
    private val repository: PrayerRepository
) {
    operator fun invoke(): PrayerData = repository.getSubuhData()
}
