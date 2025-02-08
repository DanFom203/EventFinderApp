package com.itis.feature.auth.impl.presentation.screens.signup.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.common.di.viewmodel.ViewModelKey
import com.itis.common.di.viewmodel.ViewModelModule
import com.itis.common.utils.CityFormatter
import com.itis.common.utils.CredentialsValidator
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.feature.auth.impl.domain.usecases.SignUpUseCase
import com.itis.feature.auth.impl.presentation.screens.signup.SignUpViewModel
import com.itis.feature.auth.api.utils.UsersAuthRouter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class SignUpModule {
    @Provides
    fun provideMainViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory
    ): SignUpViewModel {
        return ViewModelProvider(fragment, factory)[SignUpViewModel::class.java]
    }

    @Provides
    @[IntoMap ViewModelKey(SignUpViewModel::class)]
    fun provideRegisterViewModel(
        useCase: SignUpUseCase,
        router: UsersAuthRouter,
        exceptionHandlerDelegate: ExceptionHandlerDelegate,
        credentialsValidator: CredentialsValidator,
        cityFormatter: CityFormatter
    ): ViewModel {
        return SignUpViewModel(useCase,router, exceptionHandlerDelegate, credentialsValidator, cityFormatter)
    }
}