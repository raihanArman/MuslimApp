package com.raydev.domain.repository

import com.raydev.anabstract.state.ResponseState
import com.raydev.shared.model.City
import com.raydev.shared.model.PrayerData
import com.raydev.shared.model.SholatTime
import kotlinx.coroutines.flow.Flow

interface PrayerRepository {
    fun searchCity(city: String): Flow<ResponseState<List<City>>>
    fun getSholatTime(cityId: String, date: String): Flow<ResponseState<SholatTime>>

    fun setImsakData(prayerData: PrayerData)
    fun setSubuhData(prayerData: PrayerData)
    fun setDhuhurData(prayerData: PrayerData)
    fun setAsharData(prayerData: PrayerData)
    fun setMaghribData(prayerData: PrayerData)
    fun setIsyaData(prayerData: PrayerData)

    fun getImsakData(): PrayerData
    fun getSubuhData(): PrayerData
    fun getDhuhurData(): PrayerData
    fun getAsharData(): PrayerData
    fun getMaghribData(): PrayerData
    fun getIsyaData(): PrayerData

}