package com.raydev.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raydev.shared.database.entity.LanguageStringEntity

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
class LanguageStringConverter {
    @TypeConverter
    fun stringToSomeObjectList(data: String?): ArrayList<LanguageStringEntity>? {
        val gson = Gson()
        if (data == null) {
            return arrayListOf()
        }

        val listType = object : TypeToken<ArrayList<LanguageStringEntity>?>() {
        }.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: ArrayList<LanguageStringEntity>?): String {
        val gson = Gson()
        return gson.toJson(someObjects)
    }
}
