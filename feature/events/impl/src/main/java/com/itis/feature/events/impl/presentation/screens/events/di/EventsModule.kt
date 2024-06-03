package com.itis.feature.events.impl.presentation.screens.events.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.common.di.viewmodel.ViewModelKey
import com.itis.common.di.viewmodel.ViewModelModule
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.feature.events.impl.domain.usecase.GetCurrentEventsUseCase
import com.itis.feature.events.impl.domain.usecase.GetCurrentLocationUseCase
import com.itis.feature.events.impl.presentation.screens.events.EventsViewModel
import com.itis.feature.events.impl.utils.EventsFeatureRouter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class EventsModule {
    @Provides
    fun provideMainViewModel(fragment: Fragment, factory: ViewModelProvider.Factory): EventsViewModel {
        return ViewModelProvider(fragment, factory)[EventsViewModel::class.java]
    }

    @Provides
    @[IntoMap ViewModelKey(EventsViewModel::class)]
    fun provideEventsViewModel (
        getCurrentEventsUseCase: GetCurrentEventsUseCase,
        getCurrentLocationUseCase: GetCurrentLocationUseCase,
        router: EventsFeatureRouter,
        exceptionHandlerDelegate: ExceptionHandlerDelegate
    ): ViewModel {
        return EventsViewModel(getCurrentEventsUseCase, getCurrentLocationUseCase, router, exceptionHandlerDelegate)
    }
}