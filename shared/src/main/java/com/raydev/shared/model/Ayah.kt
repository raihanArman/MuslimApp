package com.raydev.shared.model

import androidx.compose.runtime.Immutable
import com.raydev.shared.database.entity.LanguageString

@Immutable
data class Ayah(
    val id: Int,
    val verseNumber: Int,
    var text: ArrayList<LanguageString>,
    var juz: Int,
    var hizb: Float,
    var useBismillah: Boolean? = false,
    var chapterId: Int,
    val isBookmark: Boolean,
    val isLastRead: Boolean
)
