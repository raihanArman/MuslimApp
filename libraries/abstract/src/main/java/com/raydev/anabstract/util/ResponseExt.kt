package com.raydev.anabstract.util

import com.raydev.anabstract.state.ResponseState

/**
 * @author Raihan Arman
 * @date 19/09/23
 */

fun <T> ResponseState<T>.onSuccess(action: T?.() -> Unit) {
    if (this is ResponseState.Success) {
        action.invoke(data)
    }
}

fun <T> ResponseState<T>.onLoading(action: () -> Unit) {
    if (this is ResponseState.Loading) {
        action.invoke()
    }
}

fun <T> ResponseState<T>.onFailure(action: String?.() -> Unit) {
    if (this is ResponseState.Error) {
        action.invoke(errorMessage)
    }
}

fun <T> ResponseState<T>.onEmpty(action: () -> Unit) {
    if (this is ResponseState.Empty) {
        action.invoke()
    }
}
