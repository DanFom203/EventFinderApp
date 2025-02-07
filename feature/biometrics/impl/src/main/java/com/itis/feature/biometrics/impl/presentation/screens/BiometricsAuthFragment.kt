package com.itis.feature.biometrics.impl.presentation.screens

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.common.base.BaseFragment
import com.itis.common.compose.AppTheme
import com.itis.common.di.FeatureUtils
import com.itis.feature.biometrics.api.di.BiometricsFeatureApi
import com.itis.feature.biometrics.impl.R
import com.itis.feature.biometrics.impl.databinding.FragmentBiometricsAuthBinding
import com.itis.feature.biometrics.impl.di.BiometricsFeatureComponent
import com.itis.feature.biometrics.impl.utils.BiometricExceptionHandler
import java.util.concurrent.Executor

class BiometricsAuthFragment: BaseFragment<BiometricsAuthViewModel>(R.layout.fragment_biometrics_auth) {
    private val viewBinding: FragmentBiometricsAuthBinding by viewBinding(FragmentBiometricsAuthBinding::bind)

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var exceptionHandler: BiometricExceptionHandler
    private lateinit var biometricManager: BiometricManager
    private lateinit var keyguardManager: KeyguardManager

        override fun inject() {
        FeatureUtils.getFeature<BiometricsFeatureComponent>(this, BiometricsFeatureApi::class.java)
            .biometricsAuthFactory().create(this).inject(this)
    }

    override fun initViews() {
        executor = ContextCompat.getMainExecutor(requireContext())
        exceptionHandler = BiometricExceptionHandler(requireContext())
        biometricManager = BiometricManager.from(requireContext())
        keyguardManager = requireContext().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        viewBinding.btnFingerprint.setOnClickListener {
            biometricAuth()
        }

    }

    override suspend fun subscribe(viewModel: BiometricsAuthViewModel) {
        biometricAuth()
    }

    private fun biometricAuth() {

        val authenticators = viewModel.getAuthenticators(keyguardManager)

        if (authenticators != null) {
            val biometricStatus = biometricManager.canAuthenticate(authenticators)
            if (biometricStatus == BiometricManager.BIOMETRIC_SUCCESS) {

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
                showSecuritySettingsDialog()
            }
        }
    }

    private fun showSecuritySettingsDialog() {
        viewBinding.composeView.setContent {
            var showDialog by remember { mutableStateOf(true) }

            if (showDialog) {
                AppTheme {
                    SecuritySettingsDialog(
                        onDismiss = { showDialog = false },
                        onNavigateToSignIn = {
                            viewModel.openSignInScreen()
                            showDialog = false
                        },
                        onOpenSecuritySettings = {
                            openSecuritySettings()
                            showDialog = false
                        }
                    )
                }
            }
        }
    }

    private fun openSecuritySettings() {
        val intent = Intent(Settings.ACTION_SECURITY_SETTINGS)
        startActivity(intent)
    }
}

@Composable
fun SecuritySettingsDialog(
    onDismiss: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onOpenSecuritySettings: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Security Alert") },
        text = { Text(text = "Set up the security settings for your device!") },
        confirmButton = {
            Button(
                onClick = onNavigateToSignIn,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Auth w/o Security")
            }
        },
        dismissButton = {
            Button(
                onClick = onOpenSecuritySettings,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Open Security Settings")
            }
        }
    )
}