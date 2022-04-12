package com.raydev.domain.usecase.quran

import com.raydev.domain.model.Ayat
import com.raydev.domain.repository.QuranRepository
import com.raydev.domain.util.ResponseState
import com.raydev.domain.util.UseCase
import kotlinx.coroutines.flow.Flow

class GetAyatUseCase(
    private val quranRepository: QuranRepository
): UseCase<List<Ayat>, String> {

    override fun call(params: String): Flow<ResponseState<List<Ayat>>> {
        return quranRepository.getAyat(params)
    }

}