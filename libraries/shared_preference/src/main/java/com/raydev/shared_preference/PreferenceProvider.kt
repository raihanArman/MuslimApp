package com.raydev.shared_preference

/**
 * @author Raihan Arman
 * @date 27/09/22
 */
interface PreferenceProvider {
    fun setStringToPreference(key: String, value: String?)

    fun getStringFromPreference(key: String, defaultValue: String? = null): String?

    fun setIntToPreference(key: String, value: Int)

    fun getIntFromPreference(key: String, defaultValue: Int? = null): Int

    fun setLongToPreference(key: String, value: Long)

    fun getLongFromPreference(key: String, defaultValue: Long): Long

    fun setBooleanToPreference(key: String, value: Boolean)

    fun getBooleanFromPreference(key: String, defaultValue: Boolean? = false): Boolean

    fun clearPreferences(key: String)

    fun clearPreferences()
}
