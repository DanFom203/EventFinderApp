package com.itis.eventfinderapp.di.deps

import com.example.feature.profile.api.di.ProfileFeatureApi
import com.example.feature.profile.impl.di.ProfileFeatureHolder
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
import com.itis.feature.biometrics.api.di.BiometricsFeatureApi
import com.itis.feature.biometrics.impl.di.BiometricsFeatureHolder
import com.itis.feature.events.api.di.EventsFeatureApi
import com.itis.feature.events.impl.di.EventsFeatureHolder
import com.itis.feature.notes.api.di.NotesFeatureApi
import com.itis.feature.notes.impl.di.NotesFeatureHolder

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
    @[IntoMap ClassKey(BiometricsFeatureApi::class)]
    fun provideBiometricsFeatureHolder(biometricsFeatureHolder: BiometricsFeatureHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @[IntoMap ClassKey(EventsFeatureApi::class)]
    fun provideEventsFeatureHolder(eventsFeatureHolder: EventsFeatureHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @[IntoMap ClassKey(NotesFeatureApi::class)]
    fun provideNotesFeatureHolder(notesFeatureHolder: NotesFeatureHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @[IntoMap ClassKey(ProfileFeatureApi::class)]
    fun provideProfileFeatureHolder(profileFeatureHolder: ProfileFeatureHolder): FeatureApiHolder
}