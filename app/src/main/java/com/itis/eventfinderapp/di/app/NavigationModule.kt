package com.itis.eventfinderapp.di.app

import com.itis.common.di.scope.ApplicationScope
import com.itis.eventfinderapp.navigation.Navigator
import com.itis.feature.auth.impl.utils.UsersAuthRouter
import com.itis.feature.events.impl.utils.EventsFeatureRouter
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
    fun provideEventsRouter(navigator: Navigator): EventsFeatureRouter = navigator

}