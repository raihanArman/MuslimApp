package com.raydev.dailyduas.api

import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.exception.Unexpected
import com.raydev.anabstract.state.FirestoreClientResult
import com.raydev.anabstract.state.FirestoreDomainResult
import com.raydev.dailyduas.api_infra.DailyDuasFirestoreClient
import com.raydev.dailyduas.domain.DailyDuas
import com.raydev.dailyduas.domain.GetDuasUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Raihan Arman
 * @date 20/05/24
 */
class GetDailyDuasFirestoreUseCase(
    private val client: DailyDuasFirestoreClient
) : GetDuasUseCase {
    override fun getDailyDuas(): Flow<FirestoreDomainResult<List<DailyDuas>>> {
        return flow {
            client.getDailyDuas().collect { result ->
                when (result) {
                    is FirestoreClientResult.Failure -> {
                        when (result.exception) {
                            is ConnectivityException -> {
                                emit(FirestoreDomainResult.Failure(Connectivity()))
                            }
                            else -> {
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
    }

    fun DailyDuasModel.toDomainModels() = DailyDuas(
        id = this.id,
        title = this.title,
        content = this.content,
        translate = this.translate
    )
}
