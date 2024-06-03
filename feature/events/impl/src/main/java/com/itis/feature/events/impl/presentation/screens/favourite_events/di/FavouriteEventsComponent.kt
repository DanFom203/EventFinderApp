package com.itis.feature.events.impl.presentation.screens.favourite_events.di

import androidx.fragment.app.Fragment
import com.itis.feature.events.impl.presentation.screens.favourite_events.FavouriteEventsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [FavouriteEventsModule::class]
)
interface FavouriteEventsComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment
        ): FavouriteEventsComponent
    }
    fun inject(fragment: FavouriteEventsFragment)
}