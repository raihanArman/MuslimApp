package com.randev.dzikir.util

/**
 * @author Raihan Arman
 * @date 18/06/24
 */
enum class DzikirCategory(val value: String) {
    PAGI("pagi"),
    PETANG("petang")
}

fun String.toValues(): DzikirCategory {
    return DzikirCategory.values().firstOrNull { it.value == this } ?: DzikirCategory.PETANG
}
