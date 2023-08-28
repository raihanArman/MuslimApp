package com.raydev.shared.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raydev.shared.database.entity.LanguageString
import com.raydev.shared.model.SeparatorItem

/**
 * @author Raihan Arman
 * @date 20/08/23
 */
fun ArrayList<LanguageString>.getText(): String {
    return filter { it.language == "bahasa" }.firstOrNull()?.text ?: ""
}
fun ArrayList<LanguageString>.getArabic(): String {
    return filter { it.language == "arabic" }.firstOrNull()?.text.toString().replace("،", "")
}

fun getArabicCalligraphy(mContext: Context, surah: Int): String {
    return Gson().fromJson<ArrayList<SeparatorItem>>(
        FileUtils.getJsonStringFromAssets(
            mContext,
            "json/quran/paged/separator.json"
        ),
        object :
            TypeToken<ArrayList<SeparatorItem>>() {}.type
    ).filter { it.surah == surah }.firstOrNull()?.unicode ?: ""
}
