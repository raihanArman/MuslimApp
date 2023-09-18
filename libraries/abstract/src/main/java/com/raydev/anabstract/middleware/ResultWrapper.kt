package com.raydev.anabstract.middleware

import com.raydev.anabstract.base.BaseModel
import com.raydev.anabstract.exception.ErrorCodesMapper
import com.raydev.anabstract.exception.NetworkCodes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * @author Raihan Arman
 * @date 18/09/23
 */
sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int, val message: String? = null) : ResultWrapper<Nothing>()
}

internal suspend fun <T : BaseModel> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T?
): ResultWrapper<T?> {
    return withContext(dispatcher) {
        try {
            val call = apiCall.invoke()
            ResultWrapper.Success(call)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> {
                    ResultWrapper.GenericError(
                        code = NetworkCodes.TIMEOUT_ERROR,
                        message = ErrorCodesMapper.getMessage(NetworkCodes.CONNECTION_ERROR)
                    )
                }
                is IOException -> {
                    ResultWrapper.GenericError(
                        code = NetworkCodes.CONNECTION_ERROR,
                        message = ErrorCodesMapper.getMessage(NetworkCodes.CONNECTION_ERROR)
                    )
                }
                else -> {
                    ResultWrapper.GenericError(
                        code = NetworkCodes.GENERIC_ERROR,
                        message = throwable.message
                    )
                }
            }
        }
    }
}
