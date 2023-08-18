package com.raydev.data.datasource.pref

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.raydev.shared.model.PrayerTime
import com.raydev.shared.model.PrayerData
import com.raydev.shared.util.KeyShared
import com.raydev.shared.util.KeyShared.PREF_KEY_ASR_JURISTIC
import com.raydev.shared.util.KeyShared.PREF_KEY_ASR_OFFSET
import com.raydev.shared.util.KeyShared.PREF_KEY_CALCULATION_METHOD
import com.raydev.shared.util.KeyShared.PREF_KEY_DHUHR_OFFSET
import com.raydev.shared.util.KeyShared.PREF_KEY_FAJR_OFFSET
import com.raydev.shared.util.KeyShared.PREF_KEY_HIGHER_LATITUDES
import com.raydev.shared.util.KeyShared.PREF_KEY_ISYA_OFFSET
import com.raydev.shared.util.KeyShared.PREF_KEY_MAGHRIB_OFFSET
import com.raydev.shared.util.KeyShared.PREF_KEY_PRAYERTIME
import com.raydev.shared.util.KeyShared.PREF_KEY_USER_CITY
import com.raydev.shared.util.KeyShared.PREF_KEY_USER_COORDINATES
import com.raydev.shared_preference.PreferenceProvider


private const val TAG = "SharedPreferenceSource"
class SharedPreferenceSource(
    private val sharedPreferences: PreferenceProvider
)  {

    fun setImsakData(prayerData: PrayerData){
        Log.d(TAG, "setImsakData: ${prayerData.time}")
        sharedPreferences.setStringToPreference(KeyShared.IMSAK_TIME, prayerData.time)
        sharedPreferences.setBooleanToPreference(KeyShared.IMSAK_STATUS, prayerData.checked)
    }

    fun setSubuhData(prayerData: PrayerData){
        sharedPreferences.setStringToPreference(KeyShared.SUBUH_TIME, prayerData.time)
        sharedPreferences.setBooleanToPreference(KeyShared.SUBUH_STATUS, prayerData.checked)
    }

    fun setDhuhurData(prayerData: PrayerData){
        sharedPreferences.setStringToPreference(KeyShared.DHUHUR_TIME, prayerData.time)
        sharedPreferences.setBooleanToPreference(KeyShared.DHUHUR_STATUS, prayerData.checked)
    }

    fun setAsharData(prayerData: PrayerData){
        sharedPreferences.setStringToPreference(KeyShared.ASHAR_TIME, prayerData.time)
        sharedPreferences.setBooleanToPreference(KeyShared.ASHAR_STATUS, prayerData.checked)
    }

    fun setMaghribData(prayerData: PrayerData){
        sharedPreferences.setStringToPreference(KeyShared.MAGHRIB_TIME, prayerData.time)
        sharedPreferences.setBooleanToPreference(KeyShared.MAGHRIB_STATUS, prayerData.checked)
    }

    fun setIsyaData(prayerData: PrayerData){
        sharedPreferences.setStringToPreference(KeyShared.ISYA_TIME, prayerData.time)
        sharedPreferences.setBooleanToPreference(KeyShared.ISYA_STATUS, prayerData.checked)
    }

    fun getImsakData(): PrayerData{
        val time = sharedPreferences.getStringFromPreference(KeyShared.IMSAK_TIME) ?: ""
        val checked = sharedPreferences.getBooleanFromPreference(KeyShared.IMSAK_STATUS, false)
        return PrayerData(time, checked)
    }

    fun getSubuhData(): PrayerData{
        val time = sharedPreferences.getStringFromPreference(KeyShared.SUBUH_TIME) ?: ""
        val checked = sharedPreferences.getBooleanFromPreference(KeyShared.SUBUH_STATUS, false)
        return PrayerData(time, checked)
    }

    fun getDzuhurData(): PrayerData{
        val time = sharedPreferences.getStringFromPreference(KeyShared.DHUHUR_TIME) ?: ""
        val checked = sharedPreferences.getBooleanFromPreference(KeyShared.DHUHUR_STATUS, false)
        return PrayerData(time, checked)
    }

    fun getAsharData(): PrayerData{
        val time = sharedPreferences.getStringFromPreference(KeyShared.ASHAR_TIME) ?: ""
        val checked = sharedPreferences.getBooleanFromPreference(KeyShared.ASHAR_STATUS, false)
        return PrayerData(time, checked)
    }

    fun getMaghribData(): PrayerData{
        val time = sharedPreferences.getStringFromPreference(KeyShared.MAGHRIB_TIME) ?: ""
        val checked = sharedPreferences.getBooleanFromPreference(KeyShared.MAGHRIB_STATUS, false)
        return PrayerData(time, checked)
    }

    fun getIsyaData(): PrayerData{
        val time = sharedPreferences.getStringFromPreference(KeyShared.ISYA_TIME) ?: ""
        val checked = sharedPreferences.getBooleanFromPreference(KeyShared.ISYA_STATUS, false)
        return PrayerData(time, checked)
    }

    var calculationMethod: Int
        // Default Kemenag
        get() = sharedPreferences.getIntFromPreference(PREF_KEY_CALCULATION_METHOD, 6)
        set(value) {
            sharedPreferences.setIntToPreference(PREF_KEY_CALCULATION_METHOD, value)
        }

    var asrJuristic: Int
        // Default Shafii (Standard)
        get() = sharedPreferences.getIntFromPreference(PREF_KEY_ASR_JURISTIC, 0)
        set(value) {
            sharedPreferences.setIntToPreference(PREF_KEY_ASR_JURISTIC, value)
        }

    var higherLatitudes: Int
        // Default AngleBased
        get() = sharedPreferences.getIntFromPreference(PREF_KEY_HIGHER_LATITUDES, 3)
        set(value) {
            sharedPreferences.setIntToPreference(PREF_KEY_HIGHER_LATITUDES, value)
        }

    var fajrOffset: Int
        get() = sharedPreferences.getIntFromPreference(PREF_KEY_FAJR_OFFSET, 0)
        set(value) {
            sharedPreferences.setIntToPreference(PREF_KEY_FAJR_OFFSET, value)
        }
    var dhuhrOffset: Int
        get() = sharedPreferences.getIntFromPreference(PREF_KEY_DHUHR_OFFSET, 0)
        set(value) {
            sharedPreferences.setIntToPreference(PREF_KEY_DHUHR_OFFSET, value)
        }
    var asrOffset: Int
        get() = sharedPreferences.getIntFromPreference(PREF_KEY_ASR_OFFSET, 0)
        set(value) {
            sharedPreferences.setIntToPreference(PREF_KEY_ASR_OFFSET, value)
        }
    var maghribOffset: Int
        get() = sharedPreferences.getIntFromPreference(PREF_KEY_MAGHRIB_OFFSET, 0)
        set(value) {
            sharedPreferences.setIntToPreference(PREF_KEY_MAGHRIB_OFFSET, value)
        }
    var isyaOffset: Int
        get() = sharedPreferences.getIntFromPreference(PREF_KEY_ISYA_OFFSET, 0)
        set(value) {
            sharedPreferences.setIntToPreference(PREF_KEY_ISYA_OFFSET, value)
        }

    var praytime: PrayerTime
        get() = Gson().fromJson(
            sharedPreferences.getStringFromPreference(
                PREF_KEY_PRAYERTIME, Gson().toJson(
                    PrayerTime(
                        null,
                        "00:00",
                        "00:00",
                        "00:00",
                        "00:00",
                        "00:00",
                        "00:00",
                        "00:00"
                    )
                )
            ),
            PrayerTime::class.java
        )
        set(value) {
            sharedPreferences.setStringToPreference(PREF_KEY_PRAYERTIME, Gson().toJson(value))
        }

    var userCoordinates: LatLng
        get() = Gson().fromJson(
            sharedPreferences.getStringFromPreference(
                PREF_KEY_USER_COORDINATES,
                Gson().toJson(LatLng(0.0, 0.0))
            ), LatLng::class.java
        )
        set(value) {
            sharedPreferences.setStringToPreference(PREF_KEY_USER_COORDINATES, Gson().toJson(value))
        }

    var userCity: String
        get() = sharedPreferences.getStringFromPreference(PREF_KEY_USER_CITY, "") ?: ""
        set(value) {
            sharedPreferences.setStringToPreference(PREF_KEY_USER_CITY, value)
        }

}