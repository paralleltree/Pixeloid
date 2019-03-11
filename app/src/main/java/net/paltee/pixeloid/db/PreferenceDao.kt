package net.paltee.pixeloid.db

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class PreferenceDao constructor(context: Context) {
    private val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var currentUserName: String
        get() = pref.getString(Keys.CURRENT_USER_NAME, "")!!
        set(value) = pref.edit().putString(Keys.CURRENT_USER_NAME, value).apply()

    private object Keys {
        const val CURRENT_USER_NAME = "CURRENT_USER_NAME"
    }
}
