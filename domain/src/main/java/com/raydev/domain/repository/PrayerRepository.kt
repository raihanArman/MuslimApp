package com.raydev.domain.repository

import com.raydev.anabstract.state.ResponseState
import com.raydev.shared.model.City
import com.raydev.shared.model.SholatTime
import kotlinx.coroutines.flow.Flow

interface PrayerRepository {
    fun searchCity(city: String): Flow<ResponseState<List<City>>>
    fun getSholatTime(cityId: String, date: String): Flow<ResponseState<SholatTime>>
}