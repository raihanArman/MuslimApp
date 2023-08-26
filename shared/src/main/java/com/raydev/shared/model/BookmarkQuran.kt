package com.raydev.shared.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.raydev.shared.database.entity.LanguageString
import kotlinx.android.parcel.Parcelize

data class BookmarkQuran(
    val id:Int?,
    var surah: Surah?,
    var ayah: Ayah?,
)
