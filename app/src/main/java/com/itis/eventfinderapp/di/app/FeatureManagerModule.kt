package com.itis.eventfinderapp.di.app

import com.itis.common.di.FeatureApiHolder
import com.itis.common.di.scope.ApplicationScope
import com.itis.eventfinderapp.di.deps.FeatureHolderManager
import dagger.Module
import dagger.Provides

@Module
class FeatureManagerModule {

    @ApplicationScope
    @Provides
    fun provideFeatureHolderManager(
        featureApiHolderMap: @JvmSuppressWildcards Map<Class<*>, FeatureApiHolder>
    ): FeatureHolderManager {
        return FeatureHolderManager(featureApiHolderMap)
    }
}