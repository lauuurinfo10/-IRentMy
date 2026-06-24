package com.example.irentmy.util

import android.content.Context

object PrefsManager {
    private const val PREFS = "user_prefs"
    private const val KEY_EMAIL = "user_email"
    private const val KEY_NAME = "user_name"
    private const val KEY_BIO = "user_bio"

    private fun prefs(c: Context) = c.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun saveEmail(c: Context, v: String) = prefs(c).edit().putString(KEY_EMAIL, v).apply()
    fun getEmail(c: Context): String? = prefs(c).getString(KEY_EMAIL, null)

    fun saveName(c: Context, v: String) = prefs(c).edit().putString(KEY_NAME, v).apply()
    fun getName(c: Context): String = prefs(c).getString(KEY_NAME, "") ?: ""

    fun saveBio(c: Context, v: String) = prefs(c).edit().putString(KEY_BIO, v).apply()
    fun getBio(c: Context): String = prefs(c).getString(KEY_BIO, "") ?: ""

    fun clear(c: Context) = prefs(c).edit().clear().apply()
}