package com.itis.feature.events.impl.presentation.screens.events.di

import androidx.fragment.app.Fragment
import com.itis.feature.events.impl.presentation.screens.events.EventsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [EventsModule::class]
)
interface EventsComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(
            @BindsInstance fragment: Fragment
        ): EventsComponent
    }
    fun inject(fragment: EventsFragment)
}