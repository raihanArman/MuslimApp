package com.raydev.shared_preference

import android.content.SharedPreferences

/**

 * @author User01

 * @Date 7/12/2022

 */

class PreferenceProviderImpl(
    private val preference: SharedPreferences
) : PreferenceProvider {
    override fun setStringToPreference(key: String, value: String?) {
        preference.edit().putString(key, value).apply()
    }

    override fun getStringFromPreference(key: String, defaultValue: String?): String? {
        return preference.getString(key, defaultValue)
    }

    override fun setIntToPreference(key: String, value: Int) {
        preference.edit().putInt(key, value).apply()
    }

    override fun getIntFromPreference(key: String, defaultValue: Int?): Int {
        return preference.getInt(key, defaultValue ?: 0)
    }

    override fun setLongToPreference(key: String, value: Long) {
        preference.edit().putLong(key, value).apply()
    }

    override fun getLongFromPreference(key: String, defaultValue: Long): Long {
        return preference.getLong(key, defaultValue)
    }

    override fun setBooleanToPreference(key: String, value: Boolean) {
        preference.edit().putBoolean(key, value).apply()
    }

    override fun getBooleanFromPreference(key: String, defaultValue: Boolean?): Boolean {
        return preference.getBoolean(key, defaultValue!!)
    }

    override fun clearPreferences(key: String) {
        preference.edit().remove(key).apply()
    }

    override fun clearPreferences() {
        preference.edit().clear().apply()
    }
}
