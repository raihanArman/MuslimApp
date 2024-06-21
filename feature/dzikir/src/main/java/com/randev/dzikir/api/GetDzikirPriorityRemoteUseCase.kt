package com.randev.dzikir.api

import com.randev.dzikir.domain.DzikirPriority
import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.exception.Unexpected
import com.raydev.anabstract.exception.UnexpectedException
import com.raydev.anabstract.state.FirestoreClientResult
import com.raydev.anabstract.state.FirestoreDomainResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Raihan Arman
 * @date 21/06/24
 */
class GetDzikirPriorityRemoteUseCase(
    private val client: GetDzikirPriorityHttpClient
) {
    fun load(): Flow<FirestoreDomainResult<List<DzikirPriority>>> = flow {
        client.getDzikirPriority().collect { result ->
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
                    emit(
                        FirestoreDomainResult.Success(
                            result.data.map {
                                it.toDomainModels()
                            }
                        )
                    )
                }
            }
        }
    }

    private fun DzikirPriorityModel.toDomainModels() = DzikirPriority(
        id = this.id,
        content = this.content,
        translate = this.translate
    )
}
