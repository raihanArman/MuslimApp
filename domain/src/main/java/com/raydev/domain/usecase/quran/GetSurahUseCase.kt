package com.raydev.domain.usecase.quran

import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.repository.QuranRepository
import com.raydev.shared.model.Surah
import kotlinx.coroutines.flow.Flow

class GetSurahUseCase(
    private val quranRepository: QuranRepository
) {
    operator fun invoke(): Flow<ResponseState<List<Surah>>> {
        return quranRepository.getSurah()
    }
}