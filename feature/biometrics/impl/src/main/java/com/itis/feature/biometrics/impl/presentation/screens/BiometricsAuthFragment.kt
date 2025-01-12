package com.itis.feature.biometrics.impl.presentation.screens

import android.app.KeyguardManager
import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.itis.common.base.BaseFragment
import com.itis.common.di.FeatureUtils
import com.itis.feature.biometrics.api.di.BiometricsFeatureApi
import com.itis.feature.biometrics.impl.R
import com.itis.feature.biometrics.impl.di.BiometricsFeatureComponent
import com.itis.feature.biometrics.impl.utils.BiometricExceptionHandler
import java.util.concurrent.Executor

class BiometricsAuthFragment: BaseFragment<BiometricsAuthViewModel>(R.layout.fragment_biometrics_auth) {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var exceptionHandler: BiometricExceptionHandler

    override fun inject() {
        FeatureUtils.getFeature<BiometricsFeatureComponent>(this, BiometricsFeatureApi::class.java)
            .biometricsAuthFactory().create(this).inject(this)
    }

    override fun initViews() {}

    override suspend fun subscribe(viewModel: BiometricsAuthViewModel) {
        executor = ContextCompat.getMainExecutor(requireContext())
        exceptionHandler = BiometricExceptionHandler(requireContext())

        // Проверка доступности биометрии и PIN/пароля
        val biometricManager = BiometricManager.from(requireContext())
        val keyguardManager = requireContext().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        val authenticators = viewModel.getAuthenticators(keyguardManager)

        // Проверка доступности биометрической аутентификации
        if (authenticators != null) {
            val biometricStatus = biometricManager.canAuthenticate(authenticators)
            if (biometricStatus == BiometricManager.BIOMETRIC_SUCCESS) {
                    // Если можно аутентифицировать с использованием биометрии
                    biometricPrompt = BiometricPrompt(this, executor,
                        object : BiometricPrompt.AuthenticationCallback() {
                            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                super.onAuthenticationError(errorCode, errString)
                                showToast(exceptionHandler.handleAuthenticationError(errString))
                            }

                            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                super.onAuthenticationSucceeded(result)
                                viewModel.openEventsScreen()
                                showToast(exceptionHandler.handleAuthenticationSucceeded())
                            }

                            override fun onAuthenticationFailed() {
                                super.onAuthenticationFailed()
                                showToast(exceptionHandler.handleAuthenticationFailed())
                            }
                        })

                    promptInfo = BiometricPrompt.PromptInfo.Builder()
                        .setTitle(getString(R.string.biometric_login_title))
                        .setSubtitle(getString(R.string.biometric_login_subtitle))
                        .setAllowedAuthenticators(authenticators)
                        .build()

                    biometricPrompt.authenticate(promptInfo)
            } else {
                showToast(exceptionHandler.handleBiometricStatus(biometricStatus))
            }
        }
    }
}