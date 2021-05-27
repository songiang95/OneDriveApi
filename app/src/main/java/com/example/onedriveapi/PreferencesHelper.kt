package com.example.onedriveapi

import android.content.Context
import android.content.SharedPreferences

object PreferencesHelper {

    private const val SHARED_PREFERENCES_NAME = "one_drive_demo_preference"

    const val PREF_REFRESH_TOKEN = "pref_refresh_token"
    const val PREF_ACCESS_TOKEN = "pref_access_token"

    private lateinit var sharedPreferences: SharedPreferences

    fun start(appContext: Context) {
        sharedPreferences =
            appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun putLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun putFloat(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue).toString()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun getStringSet(key: String): Set<String> {
        return sharedPreferences.getStringSet(key, HashSet())!!
    }

    fun putStringSet(key: String, value: Set<String>) {
        sharedPreferences.edit().putStringSet(key, value).apply()
    }

    fun saveRefreshToken(refreshToken: String) {
        putString(PREF_REFRESH_TOKEN, refreshToken)
    }

    fun saveAccessToken(accessToken: String) {
        putString(PREF_ACCESS_TOKEN, accessToken)
    }

    fun getRefreshToken(): String {
        return getString(PREF_REFRESH_TOKEN, "")
    }

    fun getAccessToken(): String {
        return getString(PREF_ACCESS_TOKEN, "")
    }

}