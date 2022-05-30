package com.raydev.anabstract.util

import com.raydev.anabstract.state.ResponseState
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<ResponseState<ResultType>> = flow {
        emit(ResponseState.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(ResponseState.Loading())
            val apiResponse = createCall().collect {response ->
                when (response) {
                    is ResponseState.Success -> {
                        saveCallResult(response.data!!)
                        emitAll(loadFromDB().map { ResponseState.Success(it) })
                    }
                    is ResponseState.Empty -> {
                        emitAll(loadFromDB().map { ResponseState.Success(it) })
                    }
                    is ResponseState.Error -> {
                        onFetchFailed()
                        emit(ResponseState.Error(response.errorMessage))
                    }
                }
            }

        } else {
            emitAll(loadFromDB().map { ResponseState.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ResponseState<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<ResponseState<ResultType>> = result

}