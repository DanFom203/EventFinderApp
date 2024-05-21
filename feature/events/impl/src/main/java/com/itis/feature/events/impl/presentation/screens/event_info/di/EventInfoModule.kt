package com.itis.feature.events.impl.presentation.screens.event_info.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.common.di.viewmodel.ViewModelKey
import com.itis.common.di.viewmodel.ViewModelModule
import com.itis.feature.events.impl.data.ExceptionHandlerDelegate
import com.itis.feature.events.impl.domain.usecase.GetEventInfoUseCase
import com.itis.feature.events.impl.presentation.screens.event_info.EventInfoViewModel
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
class EventInfoModule {
    @Provides
    fun provideMainViewModel(fragment: Fragment, factory: ViewModelProvider.Factory): EventInfoViewModel {
        return ViewModelProvider(fragment, factory)[EventInfoViewModel::class.java]
    }

    @Provides
    @[IntoMap ViewModelKey(EventsViewModel::class)]
    fun provideEventInfoViewModel (
        useCase: GetEventInfoUseCase,
        router: EventsFeatureRouter,
        exceptionHandlerDelegate: ExceptionHandlerDelegate
    ): ViewModel {
        return EventInfoViewModel(useCase,router,exceptionHandlerDelegate)
    }
}