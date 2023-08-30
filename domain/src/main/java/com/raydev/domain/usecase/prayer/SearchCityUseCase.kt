package com.raydev.domain.usecase.prayer

import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.repository.PrayerRepository
import com.raydev.shared.model.City
import kotlinx.coroutines.flow.Flow

class SearchCityUseCase(
    private val prayerRepository: PrayerRepository
) {
    operator fun invoke(city: String): Flow<ResponseState<List<City>>> {
        return prayerRepository.searchCity(city)
    }
}
