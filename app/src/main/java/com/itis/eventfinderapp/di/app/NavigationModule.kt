package com.itis.eventfinderapp.di.app

import com.example.feature.profile.api.utils.ProfileFeatureRouter
import dagger.Module
import dagger.Provides
import com.itis.common.di.scope.ApplicationScope
import com.itis.eventfinderapp.navigation.Navigator
import com.itis.feature.auth.api.utils.UsersAuthRouter
import com.itis.feature.biometrics.api.utils.BiometricsAuthRouter
import com.itis.feature.events.api.utils.EventsFeatureRouter
import com.itis.feature.notes.api.utils.NotesFeatureRouter

@Module
class NavigationModule {

    @ApplicationScope
    @Provides
    fun provideNavigator(): Navigator = Navigator()

    @ApplicationScope
    @Provides
    fun provideAuthRouter(navigator: Navigator): UsersAuthRouter = navigator

    @ApplicationScope
    @Provides
    fun provideBiometricsAuthRouter(navigator: Navigator): BiometricsAuthRouter = navigator

    @ApplicationScope
    @Provides
    fun provideEventsRouter(navigator: Navigator): EventsFeatureRouter = navigator

    @ApplicationScope
    @Provides
    fun provideNotesRouter(navigator: Navigator): NotesFeatureRouter = navigator

    @ApplicationScope
    @Provides
    fun provideProfileRouter(navigator: Navigator): ProfileFeatureRouter = navigator
}