package com.example.feature.profile.impl.presentation.screens.profile.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature.profile.impl.domain.usecase.GetUserProfileInfoUseCase
import com.example.feature.profile.impl.presentation.screens.profile.ProfileViewModel
import com.example.feature.profile.impl.utils.ProfileFeatureRouter
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
class ProfileModule {
    @Provides
    fun provideMainViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory
    ): ProfileViewModel {
        return ViewModelProvider(fragment, factory)[ProfileViewModel::class.java]
    }

    @Provides
    @[IntoMap ViewModelKey(ProfileViewModel::class)]
    fun provideProfileViewModel(
        useCase: GetUserProfileInfoUseCase,
        router: ProfileFeatureRouter,
        exceptionHandlerDelegate: ExceptionHandlerDelegate,
        credentialsValidator: CredentialsValidator,
        cityFormatter: CityFormatter
    ): ViewModel {
        return ProfileViewModel(useCase,router, exceptionHandlerDelegate, credentialsValidator, cityFormatter)
    }
}