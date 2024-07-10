package com.randev.dzikir.domain.model

/**
 * @author Raihan Arman
 * @date 18/06/24
 */
data class Dzikir(
    val id: String,
    val title: String,
    val content: String,
    val translate: String,
    val times: String
)
