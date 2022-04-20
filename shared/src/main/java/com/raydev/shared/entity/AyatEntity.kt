package com.raydev.shared.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "ayat")
data class AyatEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "ar") var ar: String,

    @ColumnInfo(name = "nomor") var nomor: String,
)