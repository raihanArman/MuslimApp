package com.raydev.domain.repository

import com.raydev.anabstract.state.ResponseState
import com.raydev.shared.model.Ayah
import com.raydev.shared.model.Surah
import kotlinx.coroutines.flow.Flow

interface QuranRepository {
    fun getSurah(): Flow<List<Surah>>
    fun getSurahAyah(): Flow<List<Surah>>
    fun getAyahBySurahId(surahId: Int): Flow<List<Ayah>>
    fun setupQuran(): Flow<ResponseState<Unit>>
    suspend fun checkDataIsExists(): Boolean
}
