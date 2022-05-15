package com.raydev.data.repository

import com.raydev.anabstract.state.ResponseState
import com.raydev.data.datasource.remote.PrayerRemoteDataSource
import com.raydev.domain.repository.PrayerRepository
import com.raydev.shared.model.City
import kotlinx.coroutines.flow.Flow

class PrayerRepositoryImpl(
    private val remoteDataSource: PrayerRemoteDataSource
): PrayerRepository {

    override fun searchCity(city: String): Flow<ResponseState<List<City>>> {
        return remoteDataSource.searchCity(city)
    }

}