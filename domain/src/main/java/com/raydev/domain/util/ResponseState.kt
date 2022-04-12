package com.raydev.domain.util

sealed class ResponseState<out R> {
    data class Success<out T>(val data: T) : ResponseState<T>()
    data class Loading<out T>(val data: T? = null) : ResponseState<T>()
    data class Error(val errorMessage: String) : ResponseState<Nothing>()
    object Empty : ResponseState<Nothing>()
}