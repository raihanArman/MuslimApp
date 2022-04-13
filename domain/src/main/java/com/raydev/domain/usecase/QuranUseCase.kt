package com.raydev.domain.usecase

import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.repository.QuranRepository
import com.raydev.domain.util.NoParams
import com.raydev.shared.model.Ayat
import com.raydev.shared.model.Surah
import kotlinx.coroutines.flow.Flow

class QuranUseCase(
    private val quranRepository: QuranRepository
) {

    fun getSurah(): Flow<ResponseState<List<Surah>>>{
        return quranRepository.getSurah();
    }
    fun getAyat(number: String): Flow<ResponseState<List<Ayat>>>{
        return quranRepository.getAyat(number);
    }

}