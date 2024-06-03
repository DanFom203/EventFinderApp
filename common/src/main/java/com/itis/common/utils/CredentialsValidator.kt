package com.itis.common.utils

import android.util.Patterns
import com.itis.common.R
import com.itis.common.core.resources.ResourceManager
import com.itis.common.data.exceptions.AuthException
import com.itis.common.data.exceptions.CredentialsUpdateException
import javax.inject.Inject

class CredentialsValidator @Inject constructor(
    private val resManager: ResourceManager
) {
    fun verifyCredentials(email: String?, username: String?, city: String?) {
        if (email == null && city == null && username == null) {
            throw CredentialsUpdateException(resManager.getString(R.string.error_empty_data))
        }
    }
    fun verifyEmail(email: String?): String {
        if (email.isNullOrBlank()) {
            throw AuthException.EmptyEmailField(resManager.getString(R.string.error_empty_email))
        }
        return verifyEmailUpdate(email)
    }

    fun verifyPassword(password: String?): String {
        if (password.isNullOrBlank()) {
            throw AuthException.EmptyPasswordField(resManager.getString(R.string.error_empty_password))
        }
        return verifyPasswordUpdate(password)
    }

    fun verifyUsername(username: String?): String {
        if (username.isNullOrBlank()) {
            throw AuthException.EmptyUsernameField(resManager.getString(R.string.error_empty_username))
        }
        return verifyUsernameUpdate(username)
    }

    fun verifyCity(city: String?): String {
        if (city.isNullOrBlank()) {
            throw AuthException.EmptyCityField(resManager.getString(R.string.error_empty_city))
        }

        return city
    }

    fun verifyEmailUpdate(email: String): String {
        val emailPattern = Patterns.EMAIL_ADDRESS
        if (email.isNotBlank() && !emailPattern.matcher(email).matches()) {
            throw AuthException.EmailInvalidCredentials(resManager.getString(R.string.error_invalid_email))
        }
        return email
    }

    private fun verifyPasswordUpdate(password: String): String {
        if (password.isNotBlank() && password.length < 6) {
            throw AuthException.PasswordInvalidCredentials(resManager.getString(R.string.error_invalid_password))
        }
        return password
    }

    fun verifyUsernameUpdate(username: String): String {
        if (username.isNotBlank() && username.length < 2) {
            throw AuthException.UsernameInvalidCredentials(resManager.getString(R.string.error_invalid_username))
        }
        return username
    }
}