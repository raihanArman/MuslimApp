package com.randev.dzikir.api_infra.response

/**
 * @author Raihan Arman
 * @date 18/06/24
 */
data class DzikirResponse(
    val id: String? = null,
    val title: String? = null,
    val content: String? = null,
    val translate: String? = null,
    val times: String? = null
)
