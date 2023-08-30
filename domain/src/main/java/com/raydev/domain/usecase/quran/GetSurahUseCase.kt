package com.raydev.domain.usecase.quran

import com.raydev.domain.repository.QuranRepository
import com.raydev.shared.model.Surah
import kotlinx.coroutines.flow.Flow

class GetSurahUseCase(
    private val quranRepository: QuranRepository
) {
    operator fun invoke(): Flow<List<Surah>> {
        return quranRepository.getSurah()
    }
}
