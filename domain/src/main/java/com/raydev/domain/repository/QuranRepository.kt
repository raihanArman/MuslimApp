package com.raydev.domain.repository

import com.raydev.shared.model.Ayat
import com.raydev.shared.model.Surah
import com.raydev.anabstract.state.ResponseState
import kotlinx.coroutines.flow.Flow

interface QuranRepository {
    fun getSurah(): Flow<com.raydev.anabstract.state.ResponseState<List<Surah>>>
    fun getAyat(number: String): Flow<com.raydev.anabstract.state.ResponseState<List<Ayat>>>
}