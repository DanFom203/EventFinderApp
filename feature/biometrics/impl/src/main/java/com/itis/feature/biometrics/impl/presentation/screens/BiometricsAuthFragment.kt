package com.itis.feature.biometrics.impl.presentation.screens

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.itis.common.base.BaseFragment
import com.itis.common.di.FeatureUtils
import com.itis.feature.biometrics.api.di.BiometricsFeatureApi
import com.itis.feature.biometrics.impl.R
import com.itis.feature.biometrics.impl.di.BiometricsFeatureComponent
import java.util.concurrent.Executor

class BiometricsAuthFragment: BaseFragment<BiometricsAuthViewModel>(R.layout.fragment_biometrics_auth) {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun inject() {
        FeatureUtils.getFeature<BiometricsFeatureComponent>(this, BiometricsFeatureApi::class.java)
            .biometricsAuthFactory().create(this).inject(this)
    }

    override fun initViews() {}

    override suspend fun subscribe(viewModel: BiometricsAuthViewModel) {
        executor = ContextCompat.getMainExecutor(requireContext())

        // Проверка доступности биометрии и PIN/пароля
        val biometricManager = BiometricManager.from(requireContext())
        val keyguardManager = requireContext().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    showToast("Authentication error: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    viewModel.openEventsScreen()
                    showToast("Authentication succeeded!")
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    showToast("Authentication failed")
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    when {
                        Build.VERSION.SDK_INT > Build.VERSION_CODES.Q -> {
                            setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                        }
                        keyguardManager.isDeviceSecure -> {
                            setAllowedAuthenticators(BIOMETRIC_WEAK or DEVICE_CREDENTIAL)
                        }
                    }
                }
            }
            .build()

        biometricPrompt.authenticate(promptInfo)
//        val isDeviceSecure = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            keyguardManager.isDeviceSecure
//        } else {
//            true
//        } // Проверка на наличие PIN/пароля
//
//        val authenticators = when {
//            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
//                // Android 11 (API 30) и выше: BIOMETRIC_STRONG и DEVICE_CREDENTIAL поддерживаются
//                BIOMETRIC_STRONG or DEVICE_CREDENTIAL
//            }
//            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
//                // Android 6.0 (API 23) и выше: используем только BIOMETRIC_WEAK для биометрии
//                if (isDeviceSecure) BIOMETRIC_STRONG or DEVICE_CREDENTIAL else BIOMETRIC_WEAK
//            }
//            else -> {
//                // Для Android ниже 6.0: поддержка только BIOMETRIC_WEAK (отпечаток пальца)
//                BIOMETRIC_WEAK
//            }
//        }
//
////         Проверка доступности биометрической аутентификации
//        when (biometricManager.canAuthenticate(authenticators)) {
//            BiometricManager.BIOMETRIC_SUCCESS -> {
//                // Если можно аутентифицировать с использованием биометрии
//                biometricPrompt = BiometricPrompt(this, executor,
//                    object : BiometricPrompt.AuthenticationCallback() {
//                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
//                            super.onAuthenticationError(errorCode, errString)
//                            showToast("Authentication error: $errString")
//                        }
//
//                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
//                            super.onAuthenticationSucceeded(result)
//                            viewModel.openEventsScreen()
//                            showToast("Authentication succeeded!")
//                        }
//
//                        override fun onAuthenticationFailed() {
//                            super.onAuthenticationFailed()
//                            showToast("Authentication failed")
//                        }
//                    })
//
//                promptInfo = BiometricPrompt.PromptInfo.Builder()
//                    .setTitle("Biometric login for my app")
//                    .setSubtitle("Log in using your biometric credential")
//                    .setAllowedAuthenticators(authenticators)
//                    .build()
//
//                biometricPrompt.authenticate(promptInfo)
//            }
//            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
//                // Биометрия не доступна
//                showToast("No biometric features available on this device.")
//            }
//            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
//                // Биометрия временно недоступна
//                showToast("Biometric features are currently unavailable.")
//            }
//            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
//                // Биометрия не настроена
//                showToast("Biometric features are not set.")
//            }
//        }
    }
}