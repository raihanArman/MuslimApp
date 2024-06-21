package com.randev.dzikir.api_infra

import com.randev.dzikir.api.DzikirPriorityModel
import com.randev.dzikir.api.GetDzikirPriorityHttpClient
import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.exception.UnexpectedException
import com.raydev.anabstract.state.FirestoreClientResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

/**
 * @author Raihan Arman
 * @date 21/06/24
 */
class GetDzikirPriorityFirestoreClient(
    private val service: DzikirFirestoreService
) : GetDzikirPriorityHttpClient {
    override fun getDzikirPriority(): Flow<FirestoreClientResult<List<DzikirPriorityModel>>> = flow {
        try {
            val result = service.getDzikirPriority()
            emit(
                FirestoreClientResult.Success(
                    result.map {
                        it.toModels()
                    }
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

    private fun DzikirPriorityResponse.toModels() = DzikirPriorityModel(
        id = this.id.orEmpty(),
        content = this.content.orEmpty(),
        translate = this.translate.orEmpty()
    )
}
