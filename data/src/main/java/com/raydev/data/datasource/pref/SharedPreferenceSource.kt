package com.raydev.data.datasource.pref

import android.content.SharedPreferences
import android.util.Log
import com.raydev.shared.model.PrayerData
import com.raydev.shared.util.KeyShared


private const val TAG = "SharedPreferenceSource"
class SharedPreferenceSource(
    private val sharedPreferences: SharedPreferences
)  {

    fun setImsakData(prayerData: PrayerData){
        Log.d(TAG, "setImsakData: ${prayerData.time}")
        sharedPreferences.edit().putString(KeyShared.IMSAK_TIME, prayerData.time).apply()
        sharedPreferences.edit().putBoolean(KeyShared.IMSAK_STATUS, prayerData.checked).apply()
    }

    fun setSubuhData(prayerData: PrayerData){
        sharedPreferences.edit().putString(KeyShared.SUBUH_TIME, prayerData.time).apply()
        sharedPreferences.edit().putBoolean(KeyShared.SUBUH_STATUS, prayerData.checked).apply()
    }

    fun setDhuhurData(prayerData: PrayerData){
        sharedPreferences.edit().putString(KeyShared.DHUHUR_TIME, prayerData.time).apply()
        sharedPreferences.edit().putBoolean(KeyShared.DHUHUR_STATUS, prayerData.checked).apply()
    }

    fun setAsharData(prayerData: PrayerData){
        sharedPreferences.edit().putString(KeyShared.ASHAR_TIME, prayerData.time).apply()
        sharedPreferences.edit().putBoolean(KeyShared.ASHAR_STATUS, prayerData.checked).apply()
    }

    fun setMaghribData(prayerData: PrayerData){
        sharedPreferences.edit().putString(KeyShared.MAGHRIB_TIME, prayerData.time).apply()
        sharedPreferences.edit().putBoolean(KeyShared.MAGHRIB_STATUS, prayerData.checked).apply()
    }

    fun setIsyaData(prayerData: PrayerData){
        sharedPreferences.edit().putString(KeyShared.ISYA_TIME, prayerData.time).apply()
        sharedPreferences.edit().putBoolean(KeyShared.ISYA_STATUS, prayerData.checked).apply()
    }

    fun getImsakData(): PrayerData{
        val time = sharedPreferences.getString(KeyShared.IMSAK_TIME, "") ?: ""
        val checked = sharedPreferences.getBoolean(KeyShared.IMSAK_STATUS, false)
        return PrayerData(time, checked)
    }

    fun getSubuhData(): PrayerData{
        val time = sharedPreferences.getString(KeyShared.SUBUH_TIME, "") ?: ""
        val checked = sharedPreferences.getBoolean(KeyShared.SUBUH_STATUS, false)
        return PrayerData(time, checked)
    }

    fun getDzuhurData(): PrayerData{
        val time = sharedPreferences.getString(KeyShared.DHUHUR_TIME, "") ?: ""
        val checked = sharedPreferences.getBoolean(KeyShared.DHUHUR_STATUS, false)
        return PrayerData(time, checked)
    }

    fun getAsharData(): PrayerData{
        val time = sharedPreferences.getString(KeyShared.ASHAR_TIME, "") ?: ""
        val checked = sharedPreferences.getBoolean(KeyShared.ASHAR_STATUS, false)
        return PrayerData(time, checked)
    }

    fun getMaghribData(): PrayerData{
        val time = sharedPreferences.getString(KeyShared.MAGHRIB_TIME, "") ?: ""
        val checked = sharedPreferences.getBoolean(KeyShared.MAGHRIB_STATUS, false)
        return PrayerData(time, checked)
    }

    fun getIsyaData(): PrayerData{
        val time = sharedPreferences.getString(KeyShared.ISYA_TIME, "") ?: ""
        val checked = sharedPreferences.getBoolean(KeyShared.ISYA_STATUS, false)
        return PrayerData(time, checked)
    }

}