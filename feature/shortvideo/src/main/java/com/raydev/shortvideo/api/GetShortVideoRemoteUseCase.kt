package com.raydev.shortvideo.api

import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.exception.Unexpected
import com.raydev.anabstract.exception.UnexpectedException
import com.raydev.anabstract.state.FirestoreClientResult
import com.raydev.anabstract.state.FirestoreDomainResult
import com.raydev.shortvideo.domain.GetShortVideoUseCase
import com.raydev.shortvideo.domain.ShortVideo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Raihan Arman
 * @date 14/07/24
 */

class GetShortVideoRemoteUseCase(
    private val client: GetShortVideoClient
) : GetShortVideoUseCase {
    override fun getShortVideo(): Flow<FirestoreDomainResult<List<ShortVideo>>> = flow {
        client.getShortVideo().collect { result ->
            when (result) {
                is FirestoreClientResult.Failure -> {
                    when (result.exception) {
                        is ConnectivityException -> {
                            emit(FirestoreDomainResult.Failure(Connectivity()))
                        }
                        is UnexpectedException -> {
                            emit(FirestoreDomainResult.Failure(Unexpected()))
                        }
                    }
                }
                is FirestoreClientResult.Success -> {
                    val domainModel = result.data.map {
                        toDomainModel(it)
                    }

                    emit(FirestoreDomainResult.Success(domainModel))
                }
            }
        }
    }

    private fun toDomainModel(model: ShortVideoModel?) = ShortVideo(
        id = model?.id.orEmpty(),
        title = model?.title.orEmpty(),
        url = model?.url.orEmpty(),
        description = model?.description.orEmpty()
    )
}
