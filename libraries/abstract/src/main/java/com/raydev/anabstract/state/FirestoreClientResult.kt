package com.raydev.anabstract.state

/**
 * @author Raihan Arman
 * @date 18/06/24
 */
sealed class FirestoreClientResult<T> {
    data class Success<T>(val data: T) : FirestoreClientResult<T>()
    data class Failure<T>(val exception: Exception) : FirestoreClientResult<T>()
}
