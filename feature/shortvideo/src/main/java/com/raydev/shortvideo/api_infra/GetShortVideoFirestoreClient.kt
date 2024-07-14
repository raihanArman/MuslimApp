package com.raydev.shortvideo.api_infra

import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.exception.UnexpectedException
import com.raydev.anabstract.state.FirestoreClientResult
import com.raydev.shortvideo.ShortVideoModel
import com.raydev.shortvideo.ShortVideoResponse
import com.raydev.shortvideo.api.GetShortVideoClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

/**
 * @author Raihan Arman
 * @date 14/07/24
 */
class GetShortVideoFirestoreClient(
    private val service: ShortVideoService
) : GetShortVideoClient {
    override fun getShortVideo(): Flow<FirestoreClientResult<List<ShortVideoModel>>> = flow {
        try {
            val result = service.getShortVideo()
            emit(
                FirestoreClientResult.Success(
                    result.map { toModel(it) }
                )
            )
        } catch (e: Exception) {
            when (e) {
                is IOException -> {
                    emit(FirestoreClientResult.Failure(ConnectivityException()))
                }
                else -> {
                    emit(FirestoreClientResult.Failure(UnexpectedException()))
                }
            }
        }
    }

    private fun toModel(model: ShortVideoResponse?) = ShortVideoModel(
        id = model?.id.orEmpty(),
        title = model?.title.orEmpty(),
        url = model?.url.orEmpty(),
        description = model?.description.orEmpty()
    )
}
