package com.raydev.shared.database.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
@Parcelize
data class LanguageString(
    var text: String? = null,
    var language: String? = null
): Parcelable