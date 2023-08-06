package com.raydev.anabstract.util

import com.raydev.anabstract.state.ResponseState

inline fun <R, T> ResponseState<T>.fold(
    onSuccess: (success: T?) -> R,
    onFailure: (exception: String?) -> R
): R = when (this) {
    is ResponseState.Success -> onSuccess(data)
    is ResponseState.Error -> onFailure(errorMessage)
    else -> onFailure("")
}