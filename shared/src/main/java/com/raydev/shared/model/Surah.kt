package com.raydev.shared.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.raydev.shared.database.entity.LanguageString
import kotlinx.android.parcel.Parcelize

data class Surah(
    @PrimaryKey
    val id: Int,
    val revelation: String,
    val verses: Int,
    val name: String,
    val page:Int,
    val translation: ArrayList<LanguageString>,
    var listAyah: List<Ayah>?= null
)
