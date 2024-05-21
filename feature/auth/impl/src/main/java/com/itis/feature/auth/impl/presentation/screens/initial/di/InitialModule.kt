package com.itis.feature.auth.impl.presentation.screens.initial.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.common.di.viewmodel.ViewModelKey
import com.itis.common.di.viewmodel.ViewModelModule
import com.itis.feature.auth.impl.presentation.screens.initial.InitialViewModel
import com.itis.feature.auth.impl.utils.UsersAuthRouter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class InitialModule {

    @Provides
    fun provideMainViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory
    ): InitialViewModel {
        return ViewModelProvider(fragment, factory)[InitialViewModel::class.java]
    }

    @Provides
    @[IntoMap ViewModelKey(InitialViewModel::class)]
    fun provideInitialViewModel(
        router: UsersAuthRouter
    ): ViewModel {
        return InitialViewModel(router)
    }
}