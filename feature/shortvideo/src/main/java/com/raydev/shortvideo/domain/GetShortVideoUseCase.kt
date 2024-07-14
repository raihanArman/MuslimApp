package com.raydev.shortvideo.domain

import com.raydev.anabstract.state.FirestoreDomainResult
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 14/07/24
 */
interface GetShortVideoUseCase {
    fun getShortVideo(): Flow<FirestoreDomainResult<List<ShortVideo>>>
}
