package com.raydev.dailyduas.api_infra

import com.google.firebase.firestore.FirebaseFirestoreException
import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.exception.PermissionDeniedException
import com.raydev.anabstract.exception.UnexpectedException
import com.raydev.anabstract.state.FirestoreClientResult
import com.raydev.dailyduas.api.DailyDuasModel
import com.raydev.dailyduas.api.GetDailyDuasFirestoreClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

/**
 * @author Raihan Arman
 * @date 20/05/24
 */
class DailyDuasFirestoreClient(
    private val service: DailyDuasFirestoreService
) : GetDailyDuasFirestoreClient {
    override fun getDailyDuas(): Flow<FirestoreClientResult<List<DailyDuasModel>>> {
        return flow {
            try {
                val result = service.getDailyDuas()
                emit(
                    FirestoreClientResult.Success(
                        result.map {
                            it.toModels()
                        }
                    )
                )
            } catch (exception: Exception) {
                when (exception) {
                    is IOException -> {
                        emit(FirestoreClientResult.Failure(ConnectivityException()))
                    }
                    is FirebaseFirestoreException -> {
                        when (exception.code) {
                            FirebaseFirestoreException.Code.PERMISSION_DENIED -> {
                                emit(FirestoreClientResult.Failure(PermissionDeniedException()))
                            }
                            else -> {}
                        }
                    }
                    else -> {
                        emit(FirestoreClientResult.Failure(UnexpectedException()))
                    }
                }
            }
        }
    }

    fun DailyDuasResponse.toModels() = DailyDuasModel(
        id = this.id.orEmpty(),
        title = this.title.orEmpty(),
        content = this.content.orEmpty(),
        translate = this.translate.orEmpty()
    )
}
