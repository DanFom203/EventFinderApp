package com.itis.common.storage

import android.content.Context
import android.content.SharedPreferences
import com.itis.common.core.preferences.Preferences
import javax.inject.Inject

class PreferencesImpl @Inject constructor(context: Context) : Preferences {

    companion object {
        private const val KEY_AUTH_STATUS = "auth_status"
        private const val KEY_CURRENT_USER_ID = "user_id"
    }

    val prefs: SharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE)


    override fun saveAuthStatus(flag:Boolean) {
        prefs.edit().putBoolean(KEY_AUTH_STATUS, flag).apply()
    }

    override fun getAutStatus(): Boolean {
        return prefs.getBoolean(KEY_AUTH_STATUS,false)
    }

    override fun saveCurrentUserId(userId: String) {
        prefs.edit().putString(KEY_CURRENT_USER_ID, userId).apply()
    }

    override fun getCurrentUserId(): String {
        return prefs.getString(KEY_CURRENT_USER_ID, "No current user")!!
    }
}