package com.raydev.anabstract.state

//sealed class ResponseState<out R> {
//    data class Success<out T>(val data: T) : ResponseState<T>()
//    data class Loading<out T>(val data: T? = null) : ResponseState<T>()
//    data class Error(val errorMessage: String) : ResponseState<Nothing>()
//    object Empty : ResponseState<Nothing>()
//}

sealed class ResponseState <out T>(
    val data: T?= null,
    val message: String ?= null
){
    data class Success<T>(val success: T?): ResponseState<T>(success)
    data class  Loading<T>(val loadingProgress: Int ?= null): ResponseState<T>()
    data class Error(val errorMessage: String?): ResponseState<Nothing>()
    object Empty : ResponseState<Nothing>()
}