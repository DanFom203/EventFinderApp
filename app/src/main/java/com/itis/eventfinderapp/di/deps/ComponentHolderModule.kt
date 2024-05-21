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
import com.itis.feature.events.api.di.EventsFeatureApi
import com.itis.feature.events.impl.di.EventsFeatureHolder

@Module
interface ComponentHolderModule {

    @ApplicationScope
    @Binds
    fun provideFeatureContainer(application: App): FeatureContainer

    @ApplicationScope
    @Binds
    @[IntoMap ClassKey(AuthFeatureApi::class)]
    fun provideUserFeatureHolder(userFeatureHolder: AuthFeatureHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @[IntoMap ClassKey(EventsFeatureApi::class)]
    fun providePredictionFeatureHolder(predictionFeatureHolder: EventsFeatureHolder): FeatureApiHolder

}