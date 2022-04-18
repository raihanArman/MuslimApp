package com.raydev.data.datasource.remote

import com.raydev.shared.model.Ayat
import com.raydev.shared.model.Surah
import com.raydev.data.network.QuranService
import com.raydev.anabstract.state.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class QuranRemoteDataSource(
    private val apiService: QuranService,
) {
    suspend fun getListSurah(): List<Surah> {
        return apiService.getListSurah()
    }

    suspend fun getListAyat(number: String): List<Ayat> {
        return apiService.getListAyat(number)
    }

}