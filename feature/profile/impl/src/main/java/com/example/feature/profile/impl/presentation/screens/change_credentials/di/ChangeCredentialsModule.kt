package com.example.feature.profile.impl.presentation.screens.change_credentials.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature.profile.impl.domain.usecase.UpdateUserProfileInfoUseCase
import com.example.feature.profile.impl.presentation.screens.change_credentials.ChangeCredentialsViewModel
import com.example.feature.profile.api.utils.ProfileFeatureRouter
import com.itis.common.di.viewmodel.ViewModelKey
import com.itis.common.di.viewmodel.ViewModelModule
import com.itis.common.utils.CityFormatter
import com.itis.common.utils.CredentialsValidator
import com.itis.common.utils.ExceptionHandlerDelegate
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class ChangeCredentialsModule {
    @Provides
    fun provideMainViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory
    ): ChangeCredentialsViewModel {
        return ViewModelProvider(fragment, factory)[ChangeCredentialsViewModel::class.java]
    }

    @Provides
    @[IntoMap ViewModelKey(ChangeCredentialsViewModel::class)]
    fun provideChangeCredentialsViewModel(
        useCase: UpdateUserProfileInfoUseCase,
        router: ProfileFeatureRouter,
        exceptionHandlerDelegate: ExceptionHandlerDelegate,
        credentialsValidator: CredentialsValidator,
        cityFormatter: CityFormatter
    ): ViewModel {
        return ChangeCredentialsViewModel(useCase, router, exceptionHandlerDelegate, credentialsValidator, cityFormatter)
    }
}