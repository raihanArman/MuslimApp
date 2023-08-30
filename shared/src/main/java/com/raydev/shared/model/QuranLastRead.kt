package com.raydev.shared.model

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
data class QuranLastRead(
    var surahId: Int = 1,
    var surahText: String = "Al Fatihah",
    var surahCalligraphy: String = "",
    var ayah: Int = 1,
    var page: Int? = null,
    var timestamp: Long? = 0,
    var sumAyah: Int = 7
)
