package com.raydev.domain.usecase.quran

import com.raydev.domain.repository.QuranRepository
import com.raydev.shared.model.Ayah
import kotlinx.coroutines.flow.Flow

class GetAyahBySurahIdUseCase(
    private val quranRepository: QuranRepository
) {
    operator fun invoke(surahId: Int): Flow<List<Ayah>> {
        return quranRepository.getAyahBySurahId(surahId)
    }
}
