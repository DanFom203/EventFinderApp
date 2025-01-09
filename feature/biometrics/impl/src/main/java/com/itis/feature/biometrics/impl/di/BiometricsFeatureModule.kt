package com.itis.feature.biometrics.impl.di

import com.itis.common.di.scope.FeatureScope
import com.itis.feature.biometrics.api.domain.BiometricsRepository
import com.itis.feature.biometrics.impl.data.repository.BiometricsRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class BiometricsFeatureModule {
    @Provides
    @FeatureScope
    fun provideBiometricsRepository(biometricsRepositoryImpl: BiometricsRepositoryImpl): BiometricsRepository = biometricsRepositoryImpl
}