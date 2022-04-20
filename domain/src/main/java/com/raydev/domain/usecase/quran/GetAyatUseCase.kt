package com.raydev.domain.usecase.quran

import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.repository.QuranRepository
import com.raydev.shared.model.Ayat
import kotlinx.coroutines.flow.Flow

class GetAyatUseCase(
    private val quranRepository: QuranRepository
) {
    operator fun invoke(number: String): Flow<ResponseState<List<Ayat>>> {
        return quranRepository.getAyat(number)
    }
}