package com.raydev.domain.usecase.quran

import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.repository.QuranRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
class SetupQuranUseCase(
    private val repository: QuranRepository
) {
    operator fun invoke(): Flow<ResponseState<Unit>> {
        return repository.setupQuran()
    }
}
