package com.raydev.anabstract.state

/**
 * @author Raihan Arman
 * @date 18/06/24
 */
sealed class FirestoreDomainResult<T> {
    data class Success<T>(val data: T) : FirestoreDomainResult<T>()
    data class Failure<T>(val exception: Exception) : FirestoreDomainResult<T>()
}
