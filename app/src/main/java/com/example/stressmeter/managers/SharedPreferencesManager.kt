package com.example.stressmeter.managers

import android.content.Context
import android.content.SharedPreferences

/**
 * Brought over from myRuns2 code
 */

typealias Store = SharedPreferencesManager

@Suppress("unused")
class SharedPreferencesManager {
    companion object {
        private const val DEFAULT = "DEFAULT"
        private lateinit var _sharedPreferences: SharedPreferences
        fun init(context: Context) {
            _sharedPreferences = context.getSharedPreferences(DEFAULT, Context.MODE_PRIVATE)
        }

        fun saveValue(key: String, value: Int) {
            val editor = _sharedPreferences.edit()
            editor.putInt(key, value)
            editor.apply()
        }

        fun saveValue(key: String, value: String) {
            val editor = _sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun getValue(key: String, defaultValue: Int): Int {
            return _sharedPreferences.getInt(key, defaultValue)
        }

        fun getValue(key: String, defaultValue: String = ""): String {
            return _sharedPreferences.getString(key, defaultValue)!!
        }
    }
}