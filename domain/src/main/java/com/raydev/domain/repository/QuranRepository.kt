package com.raydev.domain.repository

import com.raydev.domain.model.Ayat
import com.raydev.domain.model.Surah
import com.raydev.domain.util.ResponseState
import kotlinx.coroutines.flow.Flow

interface QuranRepository {
    fun getSurah(): Flow<ResponseState<List<Surah>>>
    fun getAyat(number: String): Flow<ResponseState<List<Ayat>>>
}