package com.raydev.domain.repository

import com.raydev.shared.model.Ayat
import com.raydev.shared.model.Surah
import com.raydev.anabstract.state.ResponseState
import kotlinx.coroutines.flow.Flow

interface QuranRepository {
    fun getSurah(): Flow<ResponseState<List<Surah>>>
    fun getAyat(number: String): Flow<ResponseState<List<Ayat>>>
}