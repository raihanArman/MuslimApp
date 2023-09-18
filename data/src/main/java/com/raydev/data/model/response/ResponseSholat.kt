package com.raydev.data.model.response

data class ResponseSholat<T>(
    val status: Boolean,
    val data: T
)
