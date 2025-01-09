package com.itis.feature.biometrics.impl.presentation.screens.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.common.data.storage.PreferencesImpl
import com.itis.common.di.viewmodel.ViewModelKey
import com.itis.common.di.viewmodel.ViewModelModule
import com.itis.feature.biometrics.impl.presentation.screens.BiometricsAuthViewModel
import com.itis.feature.biometrics.impl.utils.BiometricsAuthRouter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class BiometricsAuthModule {
    @Provides
    fun provideMainViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory
    ): BiometricsAuthViewModel {
        return ViewModelProvider(fragment, factory)[BiometricsAuthViewModel::class.java]
    }

    @Provides
    @[IntoMap ViewModelKey(BiometricsAuthViewModel::class)]
    fun provideBiometricsViewModel(
        preferencesImpl: PreferencesImpl,
        router: BiometricsAuthRouter
    ): ViewModel {
        return BiometricsAuthViewModel(preferencesImpl,router)
    }
}