package com.itis.feature.biometrics.impl.utils

import android.content.Context
import androidx.biometric.BiometricManager
import com.itis.feature.biometrics.impl.R

class BiometricExceptionHandler(private val context: Context) {

    fun handleAuthenticationError(errString: CharSequence): String {
        return context.getString(R.string.authentication_error, errString)
    }

    fun handleAuthenticationSucceeded(): String {
        return context.getString(R.string.authentication_succeeded)
    }

    fun handleAuthenticationFailed(): String {
        return context.getString(R.string.authentication_failed)
    }

    fun handleBiometricStatus(status: Int): String {
        return when (status) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> context.getString(R.string.no_biometric_features)
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> context.getString(R.string.biometric_features_unavailable)
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> context.getString(R.string.biometric_not_set)
            else -> context.getString(R.string.unknown_error)
        }
    }
}
