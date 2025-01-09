package com.itis.feature.biometrics.impl.di

import android.content.Context
import com.itis.common.di.FeatureApiHolder
import com.itis.common.di.FeatureContainer
import com.itis.common.di.scope.ApplicationScope
import com.itis.feature.biometrics.impl.utils.BiometricsAuthRouter
import javax.inject.Inject

@ApplicationScope
class BiometricsFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val biometricsAuthRouter: BiometricsAuthRouter,
    private val context: Context
) : FeatureApiHolder(featureContainer) {
    override fun initializeDependencies(): Any {
        val biometricsFeatureDependencies = DaggerBiometricsFeatureComponent_BiometricsFeatureDependenciesComponent.builder()
            .commonApi(commonApi())
            .build()
        return DaggerBiometricsFeatureComponent.builder()
            .context(context)
            .withDependencies(biometricsFeatureDependencies)
            .router(biometricsAuthRouter)
            .build()
    }
}