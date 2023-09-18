package com.raydev.anabstract.middleware

import com.raydev.anabstract.base.BaseModel
import com.raydev.anabstract.state.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

/**
 * @author Raihan Arman
 * @date 18/09/23
 */
abstract class NetworkResource<T : BaseModel> {

    fun asFlow(): Flow<ResponseState<T>> = flow {
        // check if should fetch data from remote or not
        if (shouldFetchFromRemote()) {
            emit(ResponseState.Loading())
            val remoteResponse = safeApiCall(dispatcher = Dispatchers.Default) {
                remoteFetch() // fetch the remote source provided
            }

            when (remoteResponse) {
                is ResultWrapper.Success -> {
                    if (shouldFetchRemoteAndSaveLocal()) {
                        remoteResponse.value?.let {
                            withContext(Dispatchers.Default) {
                                saveLocal(it)
                            }
                            emit(ResponseState.Success(model = remoteResponse.value))
                        }
                    } else {
                        emit(ResponseState.Success(model = remoteResponse.value))
                    }
                }
                is ResultWrapper.GenericError -> {
                    if (shouldFetchLocalOnError()) {
                        val localData = withContext(Dispatchers.Default) {
                            localFetch()
                        }
                        emit(ResponseState.Success(model = localData))
                    } else {
                        emit(
                            ResponseState.Error(
                                errorMessage = remoteResponse.message ?: ""
                            )
                        )
                    }
                }
            }
        } else {
            // get from cache
            val localData = withContext(Dispatchers.Default) {
                localFetch()
            }
            emit(ResponseState.Success(model = localData))
        }
    }

    abstract suspend fun remoteFetch(): T
    open suspend fun saveLocal(data: T) {}
    open suspend fun localFetch(): T? = null
    open fun onFetchFailed(throwable: Throwable) = Unit
    open fun shouldFetchFromRemote() = true
    open fun shouldFetchRemoteAndSaveLocal() = false
    open fun shouldFetchLocalOnError() = false
}
