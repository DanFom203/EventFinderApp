package com.itis.common.core.preferences

interface Preferences {

    fun saveAuthStatus(flag: Boolean)

    fun getAutStatus(): Boolean

    fun saveCurrentUserId(userId: String?)

    fun getCurrentUserId(): String
}