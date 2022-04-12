package com.raydev.domain.usecase.quran

import com.raydev.domain.model.Surah
import com.raydev.domain.repository.QuranRepository
import com.raydev.domain.util.NoParams
import com.raydev.domain.util.ResponseState
import com.raydev.domain.util.UseCase
import kotlinx.coroutines.flow.Flow

class GetSurahUseCase(
    private val quranRepository: QuranRepository
): UseCase<List<Surah>, NoParams> {
    override fun call(params: NoParams): Flow<ResponseState<List<Surah>>> {
        return quranRepository.getSurah()
    }
}