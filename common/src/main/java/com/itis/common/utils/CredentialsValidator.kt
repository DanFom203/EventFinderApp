package com.itis.common.utils

import android.util.Patterns
import com.itis.common.R
import com.itis.common.core.resources.ResourceManager
import com.itis.common.data.exceptions.AuthException
import javax.inject.Inject

class CredentialsValidator @Inject constructor(
    private val resManager: ResourceManager
) {
    fun verifyEmail(email: String?): String {
        if (email.isNullOrBlank()) {
            throw AuthException.EmptyEmailField(resManager.getString(R.string.error_empty_email))
        }

        val emailPattern = Patterns.EMAIL_ADDRESS
        if (!emailPattern.matcher(email).matches()) {
            throw AuthException.EmailInvalidCredentials(resManager.getString(R.string.error_invalid_email))
        }

        return email
    }

    fun verifyPassword(password: String?): String {
        if (password.isNullOrBlank()) {
            throw AuthException.EmptyPasswordField(resManager.getString(R.string.error_empty_password))
        }

        if (password.length < 6) {
            throw AuthException.PasswordInvalidCredentials(resManager.getString(R.string.error_invalid_password))
        }

        return password
    }

    fun verifyUsername(username: String?): String {
        if (username.isNullOrBlank()) {
            throw AuthException.EmptyUsernameField(resManager.getString(R.string.error_empty_username))
        }

        if (username.length < 2) {
            throw AuthException.UsernameInvalidCredentials(resManager.getString(R.string.error_invalid_username))
        }

        return username
    }

    fun verifyCity(city: String?): String {
        if (city.isNullOrBlank()) {
            throw AuthException.EmptyUsernameField(resManager.getString(R.string.error_empty_city))
        }

        return city
    }
}