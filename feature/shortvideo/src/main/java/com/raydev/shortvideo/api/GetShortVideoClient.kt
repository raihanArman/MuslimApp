package com.raydev.shortvideo.api

import com.raydev.anabstract.state.FirestoreClientResult
import com.raydev.shortvideo.ShortVideoModel
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 14/07/24
 */
interface GetShortVideoClient {
    fun getShortVideo(): Flow<FirestoreClientResult<List<ShortVideoModel>>>
}
