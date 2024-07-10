package com.randev.dzikir.api_infra.client

import com.randev.dzikir.api.client.GetDzikirHttpClient
import com.randev.dzikir.api.model.DzikirModel
import com.randev.dzikir.api.request.DzikirRequestDto
import com.randev.dzikir.api_infra.response.DzikirResponse
import com.randev.dzikir.api_infra.service.DzikirFirestoreService
import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.exception.UnexpectedException
import com.raydev.anabstract.state.FirestoreClientResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

/**
 * @author Raihan Arman
 * @date 18/06/24
 */
class GetDzikirFirestoreClient(
    private val service: DzikirFirestoreService
) : GetDzikirHttpClient {
    override fun getDzikir(request: DzikirRequestDto): Flow<FirestoreClientResult<List<DzikirModel>>> = flow {
        try {
            val result = service.getDzikir(request.category.value)
            emit(
                FirestoreClientResult.Success(
                    result.map {
                        it.toModels()
                    },
                ),
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

    private fun DzikirResponse.toModels() = DzikirModel(
        id = this.id.orEmpty(),
        title = this.title.orEmpty(),
        content = this.content.orEmpty(),
        translate = this.translate.orEmpty(),
        times = this.times.orEmpty(),
    )
}
