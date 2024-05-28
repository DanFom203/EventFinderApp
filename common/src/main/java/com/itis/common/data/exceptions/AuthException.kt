package com.itis.common.data.exceptions

sealed class AuthException(message: String?) : Exception(message) {
    class EmailInvalidCredentials(message: String) : Throwable(message = message)
    class PasswordInvalidCredentials(message: String) : Throwable(message = message)
    class InvalidCredentials(message: String) : Throwable(message = message)
    class EmptyEmailField(message: String) : Throwable(message = message)
    class EmptyPasswordField(message: String) : Throwable(message = message)
    class EmptyUsernameField(message: String) : Throwable(message = message)
    class EmptyCityField(message: String) : Throwable(message = message)
    class UsernameInvalidCredentials(message: String) : Throwable(message = message)
}