package com.itis.feature.events.impl.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
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
    private val api: KudagoApi,
    private val db: FirebaseFirestore,
    private val context: Context
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
        val eventsFeatureDependencies =
            DaggerEventsFeatureComponent_EventsFeatureDependenciesComponent.builder()
                .commonApi(commonApi())
                .build()
        return DaggerEventsFeatureComponent.builder()
            .withDependencies(eventsFeatureDependencies)
            .context(context)
            .api(api)
            .db(db)
            .router(router)
            .build()
    }
}