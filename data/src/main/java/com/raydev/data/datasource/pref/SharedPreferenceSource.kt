package com.raydev.data.datasource.pref

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.raydev.shared.model.PrayerTime
import com.raydev.shared.model.PrayerData
import com.raydev.shared.model.RingType
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
        sharedPreferences.setIntToPreference(KeyShared.IMSAK_STATUS, prayerData.ringType)
    }

    fun setSubuhData(prayerData: PrayerData){
        sharedPreferences.setIntToPreference(KeyShared.SUBUH_STATUS, prayerData.ringType)
    }

    fun setDhuhurData(prayerData: PrayerData){
        sharedPreferences.setIntToPreference(KeyShared.DHUHUR_STATUS, prayerData.ringType)
    }

    fun setAsharData(prayerData: PrayerData){
        sharedPreferences.setIntToPreference(KeyShared.ASHAR_STATUS, prayerData.ringType)
    }

    fun setMaghribData(prayerData: PrayerData){
        sharedPreferences.setIntToPreference(KeyShared.MAGHRIB_STATUS, prayerData.ringType)
    }

    fun setIsyaData(prayerData: PrayerData){
        sharedPreferences.setIntToPreference(KeyShared.ISYA_STATUS, prayerData.ringType)
    }

    fun getImsakData(): PrayerData{
        val checked = sharedPreferences.getIntFromPreference(KeyShared.IMSAK_STATUS, RingType.SOUND)
        return PrayerData(checked)
    }

    fun getSubuhData(): PrayerData{
        val checked = sharedPreferences.getIntFromPreference(KeyShared.SUBUH_STATUS, RingType.SOUND)
        return PrayerData(checked)
    }

    fun getDzuhurData(): PrayerData{
        val checked = sharedPreferences.getIntFromPreference(KeyShared.DHUHUR_STATUS, RingType.SOUND)
        return PrayerData(checked)
    }

    fun getAsharData(): PrayerData{
        val checked = sharedPreferences.getIntFromPreference(KeyShared.ASHAR_STATUS, RingType.SOUND)
        return PrayerData(checked)
    }

    fun getMaghribData(): PrayerData{
        val checked = sharedPreferences.getIntFromPreference(KeyShared.MAGHRIB_STATUS, RingType.SOUND)
        return PrayerData(checked)
    }

    fun getIsyaData(): PrayerData{
        val checked = sharedPreferences.getIntFromPreference(KeyShared.ISYA_STATUS, RingType.SOUND)
        return PrayerData(checked)
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