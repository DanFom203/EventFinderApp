package com.itis.feature.biometrics.impl.di

import android.content.Context
import com.itis.common.di.CommonApi
import com.itis.common.di.scope.FeatureScope
import com.itis.feature.biometrics.api.di.BiometricsFeatureApi
import com.itis.feature.biometrics.impl.presentation.screens.di.BiometricsAuthComponent.BiometricsComponent
import com.itis.feature.biometrics.impl.utils.BiometricsAuthRouter
import dagger.BindsInstance
import dagger.Component


@FeatureScope
@Component(
    dependencies = [BiometricsFeatureDependencies::class]
)
interface BiometricsFeatureComponent : BiometricsFeatureApi {

    fun biometricsAuthFactory(): BiometricsComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        @BindsInstance
        fun router(biometricsAuthRouter: BiometricsAuthRouter): Builder
        fun withDependencies(deps: BiometricsFeatureDependencies): Builder
        fun build(): BiometricsFeatureComponent
    }

    @Component(
        dependencies = [
            CommonApi::class
        ]
    )
    interface BiometricsFeatureDependenciesComponent : BiometricsFeatureDependencies

}