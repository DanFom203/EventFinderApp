package com.itis.feature.biometrics.impl.presentation.screens

import android.app.KeyguardManager
import android.os.Build
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import com.itis.common.base.BaseViewModel
import com.itis.common.data.storage.PreferencesImpl
import com.itis.feature.biometrics.api.utils.BiometricsAuthRouter

class BiometricsAuthViewModel(
    private val preferencesImpl: PreferencesImpl,
    private val router: BiometricsAuthRouter
): BaseViewModel() {

    fun getAuthenticators(keyguardManager : KeyguardManager): Int? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                Build.VERSION.SDK_INT > Build.VERSION_CODES.Q -> {
                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                }
                keyguardManager.isDeviceSecure -> {
                    BIOMETRIC_WEAK or DEVICE_CREDENTIAL
                }
                else -> null
            }
        } else {
            null
        }
    }

    fun openSignInScreen() {
        router.openSignInScreenFromBiometrics()
    }

    fun openEventsScreen() {
        router.openEventsScreenFromBiometrics()
    }
}