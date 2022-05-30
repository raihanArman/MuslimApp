package com.raydev.data.repository

import com.raydev.anabstract.state.ResponseState
import com.raydev.data.datasource.pref.SharedPreferenceSource
import com.raydev.data.datasource.remote.PrayerRemoteDataSource
import com.raydev.domain.repository.PrayerRepository
import com.raydev.shared.model.City
import com.raydev.shared.model.PrayerData
import com.raydev.shared.model.SholatTime
import kotlinx.coroutines.flow.Flow

class PrayerRepositoryImpl(
    private val remoteDataSource: PrayerRemoteDataSource,
    private val sharedPreferenceSource: SharedPreferenceSource
): PrayerRepository {

    override fun searchCity(city: String): Flow<ResponseState<List<City>>> {
        return remoteDataSource.searchCity(city)
    }

    override fun getSholatTime(cityId: String, date: String): Flow<ResponseState<SholatTime>> {
        return remoteDataSource.getSholatTime(cityId, date)
    }

    override fun setImsakData(prayerData: PrayerData) {
        sharedPreferenceSource.setImsakData(prayerData)
    }

    override fun setSubuhData(prayerData: PrayerData) {
        sharedPreferenceSource.setSubuhData(prayerData)
    }

    override fun setDhuhurData(prayerData: PrayerData) {
        sharedPreferenceSource.setDhuhurData(prayerData)
    }

    override fun setAsharData(prayerData: PrayerData) {
        sharedPreferenceSource.setAsharData(prayerData)
    }

    override fun setMaghribData(prayerData: PrayerData) {
        sharedPreferenceSource.setMaghribData(prayerData)
    }

    override fun setIsyaData(prayerData: PrayerData) {
        sharedPreferenceSource.setIsyaData(prayerData)
    }

    override fun getImsakData(): PrayerData = sharedPreferenceSource.getImsakData()

    override fun getSubuhData(): PrayerData = sharedPreferenceSource.getSubuhData()

    override fun getDhuhurData(): PrayerData = sharedPreferenceSource.getDzuhurData()

    override fun getAsharData(): PrayerData = sharedPreferenceSource.getAsharData()

    override fun getMaghribData(): PrayerData = sharedPreferenceSource.getMaghribData()

    override fun getIsyaData(): PrayerData = sharedPreferenceSource.getIsyaData()

}