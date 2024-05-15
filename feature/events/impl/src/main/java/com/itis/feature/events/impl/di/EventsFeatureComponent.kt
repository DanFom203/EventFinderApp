package com.itis.feature.events.impl.di

import com.itis.common.di.scope.FeatureScope
import com.itis.feature.events.api.di.EventsFeatureApi
import com.itis.feature.events.impl.presentation.screens.events.di.EventsComponent
import dagger.Component

@FeatureScope
@Component(
    modules = [EventsFeatureModule::class],
    dependencies = [EventsFeatureDependencies::class]
)
interface EventsFeatureComponent: EventsFeatureApi {

    fun eventsComponentFactory(): EventsComponent.Factory
}