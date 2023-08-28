package com.raydev.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raydev.shared.database.entity.AyahLineWord

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
class AyahLineWordConverter {
    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<AyahLineWord>? {
        val gson = Gson()
        if (data == null) {
            return arrayListOf()
        }

        val listType = object : TypeToken<List<AyahLineWord>?>() {
        }.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<AyahLineWord>?): String {
        val gson = Gson()
        return gson.toJson(someObjects)
    }
}
