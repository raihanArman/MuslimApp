package com.raydev.shared.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity(tableName = "surah")
data class SurahEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "nomor")
    var nomor: String,

    @ColumnInfo(name = "arti")
    var arti: String,

    @ColumnInfo(name = "asma")
    var asma: String,

    @ColumnInfo(name = "ayat")
    var ayat: String,

    @ColumnInfo(name = "nama")
    var nama: String,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "audio")
    var audio: String,

    @ColumnInfo(name = "keterangan")
    var keterangan: String
)