package com.randev.dzikir.api.usecase

import com.randev.dzikir.api.client.GetDzikirHttpClient
import com.randev.dzikir.api.model.DzikirModel
import com.randev.dzikir.api.request.DzikirRequestDto
import com.randev.dzikir.domain.model.Dzikir
import com.randev.dzikir.domain.request.DzikirRequest
import com.randev.dzikir.domain.usecase.GetDzikirUseCase
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
 * @date 18/06/24
 */
class GetDzikirRemoteUseCase(
    private val client: GetDzikirHttpClient
) : GetDzikirUseCase {
    override fun load(request: DzikirRequest): Flow<FirestoreDomainResult<List<Dzikir>>> = flow {
        client.getDzikir(toDtoRequest(request)).collect { result ->
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

    private fun DzikirModel.toDomainModels() = Dzikir(
        id = this.id,
        title = this.title,
        content = this.content,
        translate = this.translate,
        times = this.times,
    )

    private fun toDtoRequest(request: DzikirRequest) = DzikirRequestDto(
        category = request.category
    )
}
