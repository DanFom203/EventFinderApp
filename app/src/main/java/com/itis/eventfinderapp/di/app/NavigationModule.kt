package com.itis.eventfinderapp.di.app

import com.example.feature.profile.impl.utils.ProfileFeatureRouter
import com.itis.common.di.scope.ApplicationScope
import com.itis.eventfinderapp.navigation.Navigator
import com.itis.feature.auth.impl.utils.UsersAuthRouter
import com.itis.feature.biometrics.impl.utils.BiometricsAuthRouter
import com.itis.feature.events.impl.utils.EventsFeatureRouter
import com.itis.feature.notes.impl.utils.NotesFeatureRouter
import dagger.Module
import dagger.Provides

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