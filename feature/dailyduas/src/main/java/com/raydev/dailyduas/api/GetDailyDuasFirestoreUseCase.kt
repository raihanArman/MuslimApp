package com.raydev.dailyduas.api

import com.raydev.dailyduas.domain.Connectivity
import com.raydev.dailyduas.domain.DailyDuas
import com.raydev.dailyduas.domain.FirestoreDomainResult
import com.raydev.dailyduas.domain.GetDuasUseCase
import com.raydev.dailyduas.domain.Unexpected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Raihan Arman
 * @date 20/05/24
 */
class GetDailyDuasFirestoreUseCase(
    private val client: FirestoreClient
) : GetDuasUseCase {
    override fun getDailyDuas(): Flow<FirestoreDomainResult> {
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
                                result.root.map {
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
        content = this.content
    )
}
