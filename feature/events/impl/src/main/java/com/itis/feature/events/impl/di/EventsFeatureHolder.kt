package com.itis.feature.events.impl.di

import com.itis.common.di.FeatureApiHolder
import com.itis.common.di.FeatureContainer
import com.itis.common.di.scope.ApplicationScope
import com.itis.feature.events.impl.utils.EventsFeatureRouter
import com.itis.feature.kudago.api.remote.KudagoApi
import javax.inject.Inject

@ApplicationScope
class EventsFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val router: EventsFeatureRouter,
    private val api: KudagoApi
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
        val eventsFeatureDependencies =
            DaggerEventsFeatureComponent_EventsFeatureDependenciesComponent.builder()
                .commonApi(commonApi())
                .build()
        return DaggerEventsFeatureComponent.builder()
            .withDependencies(eventsFeatureDependencies)
            .api(api)
            .router(router)
            .build()
    }
}