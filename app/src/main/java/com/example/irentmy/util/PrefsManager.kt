package com.example.irentmy.util

import android.content.Context

object PrefsManager {
    private const val PREFS = "user_prefs"
    private const val KEY_EMAIL = "user_email"

    private fun prefs(c: Context) = c.getSharedPreferences(PREFS, Context.MODE_PRIVATE)


    fun saveEmail(c: Context, v: String) = prefs(c).edit().putString(KEY_EMAIL, v).apply()
    fun getEmail(c: Context): String = prefs(c).getString(KEY_EMAIL, "") ?: ""


    fun saveName(c: Context, email: String, v: String) =
        prefs(c).edit().putString("name_$email", v).apply()
    fun getName(c: Context, email: String): String =
        prefs(c).getString("name_$email", "") ?: ""

    fun saveBio(c: Context, email: String, v: String) =
        prefs(c).edit().putString("bio_$email", v).apply()
    fun getBio(c: Context, email: String): String =
        prefs(c).getString("bio_$email", "") ?: ""


    fun clearSession(c: Context) = prefs(c).edit().remove(KEY_EMAIL).apply()
}