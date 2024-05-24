package com.itis.feature.events.impl.di

import com.google.firebase.firestore.FirebaseFirestore
import com.itis.common.di.scope.FeatureScope
import com.itis.feature.events.api.domain.repository.EventsRepository
import com.itis.feature.events.impl.data.repository.EventsRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class EventsFeatureModule {
    @Provides
    @FeatureScope
    fun provideEventsRepository(eventsRepositoryImpl: EventsRepositoryImpl): EventsRepository = eventsRepositoryImpl

    @Provides
    @FeatureScope
    fun provideFirebaseAuth(): FirebaseFirestore = FirebaseFirestore.getInstance()
}