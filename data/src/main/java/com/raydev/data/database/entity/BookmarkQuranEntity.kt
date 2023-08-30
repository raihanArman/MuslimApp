package com.raydev.data.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
@Entity(tableName = "bookmark_quran")
@Parcelize
data class BookmarkQuranEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    @ColumnInfo(name = "surahId")
    var surahId: Int,
    @ColumnInfo(name = "ayahId")
    var ayahId: Int,
) : Parcelable
