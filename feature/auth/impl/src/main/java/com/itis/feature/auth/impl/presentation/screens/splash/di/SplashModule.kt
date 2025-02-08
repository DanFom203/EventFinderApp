package com.itis.feature.auth.impl.presentation.screens.splash.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.common.di.viewmodel.ViewModelKey
import com.itis.common.di.viewmodel.ViewModelModule
import com.itis.common.data.storage.PreferencesImpl
import com.itis.feature.auth.impl.presentation.screens.splash.SplashScreenViewModel
import com.itis.feature.auth.api.utils.UsersAuthRouter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class SplashModule {
    @Provides
    fun provideMainViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory
    ): SplashScreenViewModel {
        return ViewModelProvider(fragment, factory)[SplashScreenViewModel::class.java]
    }

    @Provides
    @[IntoMap ViewModelKey(SplashScreenViewModel::class)]
    fun provideRegisterViewModel(
        preferencesImpl: PreferencesImpl,
        router: UsersAuthRouter
    ): ViewModel {
        return SplashScreenViewModel(preferencesImpl,router)
    }
}