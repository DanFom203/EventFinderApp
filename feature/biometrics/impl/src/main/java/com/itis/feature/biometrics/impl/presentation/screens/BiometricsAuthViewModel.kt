package com.itis.feature.biometrics.impl.presentation.screens

import com.itis.common.base.BaseViewModel
import com.itis.common.data.storage.PreferencesImpl
import com.itis.feature.biometrics.impl.utils.BiometricsAuthRouter

class BiometricsAuthViewModel(
    private val preferencesImpl: PreferencesImpl,
    private val router: BiometricsAuthRouter
): BaseViewModel() {

    fun openEventsScreen() {
        router.openEventsScreenFromBiometrics()
    }
}