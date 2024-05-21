package com.itis.feature.events.impl.presentation.screens.event_info.di

import androidx.fragment.app.Fragment
import com.itis.feature.events.impl.presentation.screens.event_info.EventInfoFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [EventInfoModule::class]
)
interface EventInfoComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(
            @BindsInstance fragment: Fragment
        ): EventInfoComponent
    }
    fun inject(fragment: EventInfoFragment)
}