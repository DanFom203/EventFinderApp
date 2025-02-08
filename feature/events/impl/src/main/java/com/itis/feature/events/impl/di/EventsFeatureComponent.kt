package com.itis.feature.events.impl.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.itis.common.di.CommonApi
import com.itis.common.di.scope.FeatureScope
import com.itis.feature.events.api.di.EventsFeatureApi
import com.itis.feature.events.impl.presentation.screens.event_info.di.EventInfoComponent
import com.itis.feature.events.impl.presentation.screens.events.di.EventsComponent
import com.itis.feature.events.impl.presentation.screens.favourite_events.di.FavouriteEventsComponent
import com.itis.feature.events.api.utils.EventsFeatureRouter
import com.itis.feature.kudago.api.remote.KudagoApi
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    modules = [EventsFeatureModule::class],
    dependencies = [EventsFeatureDependencies::class]
)
interface EventsFeatureComponent: EventsFeatureApi {

    fun eventsComponentFactory(): EventsComponent.Factory

    fun eventInfoComponentFactory(): EventInfoComponent.Factory

    fun favouriteEventsComponentFactory(): FavouriteEventsComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        @BindsInstance
        fun api(pandasScoreApi: KudagoApi): Builder
        @BindsInstance
        fun db(firestore: FirebaseFirestore): Builder
        @BindsInstance
        fun router(predictionFeatureRouter: EventsFeatureRouter): Builder
        fun withDependencies(deps: EventsFeatureDependencies): Builder
        fun build(): EventsFeatureComponent
    }

    @Component(
        dependencies = [
            CommonApi::class
        ]
    )
    interface EventsFeatureDependenciesComponent : EventsFeatureDependencies
}