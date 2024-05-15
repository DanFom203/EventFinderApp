package com.itis.eventfinderapp.di.deps

import com.itis.common.di.FeatureApiHolder
import com.itis.common.di.FeatureContainer
import com.itis.common.di.scope.ApplicationScope
import com.itis.eventfinderapp.App
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import com.itis.feature.auth.api.di.AuthFeatureApi
import com.itis.feature.auth.impl.di.AuthFeatureHolder

@Module
interface ComponentHolderModule {

    @ApplicationScope
    @Binds
    fun provideFeatureContainer(application: App): FeatureContainer

    @ApplicationScope
    @Binds
    @ClassKey(AuthFeatureApi::class)
    @IntoMap
    fun provideUserFeatureHolder(userFeatureHolder: AuthFeatureHolder): FeatureApiHolder

}