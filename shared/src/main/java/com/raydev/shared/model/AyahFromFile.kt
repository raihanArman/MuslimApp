package com.raydev.shared.model

import com.google.gson.annotations.SerializedName

/**
 * @author Raihan Arman
 * @date 29/08/23
 */
data class AyahFromFile(
    val id: Int,
    @SerializedName("verse_number")
    val verseNumber: Int,
    var text: ArrayList<LanguageStringFromFile> = ArrayList(),
    var juz: Int,
    var hizb: Float,
    @SerializedName("use_bismillah")
    var useBismillah: Boolean? = false,
    var chapterId: Int
)

data class LanguageStringFromFile(
    var text: String,
    var language: String
)
