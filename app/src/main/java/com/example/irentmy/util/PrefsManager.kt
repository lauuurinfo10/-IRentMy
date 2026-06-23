package com.example.irentmy.util

import android.content.Context

object PrefsManager {
    private const val PREFS = "user_prefs"
    private const val KEY_EMAIL = "user_email"

    fun saveEmail(context: Context, email: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(KEY_EMAIL, email).apply()
    }

    fun getEmail(context: Context): String? =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY_EMAIL, null)

    fun clear(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().clear().apply()
    }
}