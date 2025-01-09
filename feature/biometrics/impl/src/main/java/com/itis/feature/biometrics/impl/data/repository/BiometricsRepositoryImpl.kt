package com.itis.feature.biometrics.impl.data.repository

import com.itis.common.data.storage.PreferencesImpl
import com.itis.feature.biometrics.api.domain.BiometricsRepository
import javax.inject.Inject

class BiometricsRepositoryImpl @Inject constructor(
    private val preferencesImpl: PreferencesImpl,
) : BiometricsRepository {

}