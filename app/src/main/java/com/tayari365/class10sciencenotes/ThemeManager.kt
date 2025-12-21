package com.tayari365.class10sciencenotes.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    private const val THEME_PREFS = "theme_prefs"
    private const val IS_DARK_MODE = "is_dark_mode"

    fun applyTheme(context: Context) {
        val sharedPrefs = context.getSharedPreferences(THEME_PREFS, Context.MODE_PRIVATE)
        val isDarkMode = sharedPrefs.getBoolean(IS_DARK_MODE, false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun toggleTheme(context: Context) {
        val sharedPrefs = context.getSharedPreferences(THEME_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        val newNightMode = when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_NO
            else -> AppCompatDelegate.MODE_NIGHT_YES
        }

        AppCompatDelegate.setDefaultNightMode(newNightMode)
        editor.putBoolean(IS_DARK_MODE, newNightMode == AppCompatDelegate.MODE_NIGHT_YES)
        editor.apply()
    }

    fun isDarkMode(context: Context): Boolean {
        val sharedPrefs = context.getSharedPreferences(THEME_PREFS, Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean(IS_DARK_MODE, false)
    }
}

