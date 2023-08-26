package com.raydev.shared.model

/**
 * @author Raihan Arman
 * @date 20/08/23
 */
data class SeparatorItem(
    val page: Int,
    val surah: Int? = null,
    val line: Int,
    val bismillah: Boolean? = false,
    val unicode: String? = ""
)
