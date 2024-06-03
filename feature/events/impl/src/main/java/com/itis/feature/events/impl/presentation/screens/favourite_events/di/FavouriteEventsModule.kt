package com.itis.feature.events.impl.presentation.screens.favourite_events.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.feature.events.impl.presentation.screens.favourite_events.FavouriteEventsViewModel
import com.itis.common.di.viewmodel.ViewModelKey
import com.itis.common.di.viewmodel.ViewModelModule
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.feature.events.impl.domain.usecase.GetUsersFavouriteEventsUseCase
import com.itis.feature.events.impl.utils.EventsFeatureRouter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class FavouriteEventsModule {
    @Provides
    fun provideMainViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory
    ): FavouriteEventsViewModel {
        return ViewModelProvider(fragment, factory)[FavouriteEventsViewModel::class.java]
    }

    @Provides
    @[IntoMap ViewModelKey(FavouriteEventsViewModel::class)]
    fun provideFavouriteEventsViewModel(
        getUsersFavouriteEventsUseCase: GetUsersFavouriteEventsUseCase,
        router: EventsFeatureRouter,
        exceptionHandlerDelegate: ExceptionHandlerDelegate
    ): ViewModel {
        return FavouriteEventsViewModel(
            getUsersFavouriteEventsUseCase,
            router,
            exceptionHandlerDelegate
        )
    }
}