package com.raydev.domain.usecase.prayer

import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.repository.PrayerRepository
import com.raydev.shared.model.SholatTime
import kotlinx.coroutines.flow.Flow

class GetSholatTimeUseCase(
    private val prayerRepository: PrayerRepository
) {
    operator fun invoke(cityId: String, date: String): Flow<ResponseState<SholatTime>> {
        return prayerRepository.getSholatTime(cityId, date)
    }
}
