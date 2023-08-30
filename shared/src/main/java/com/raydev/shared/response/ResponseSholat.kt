package com.raydev.shared.response

data class ResponseSholat<T>(
    val status: Boolean,
    val data: T
)
