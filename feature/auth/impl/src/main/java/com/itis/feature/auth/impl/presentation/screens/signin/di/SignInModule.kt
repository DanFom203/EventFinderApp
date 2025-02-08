package com.itis.feature.auth.impl.presentation.screens.signin.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.common.di.viewmodel.ViewModelKey
import com.itis.common.di.viewmodel.ViewModelModule
import com.itis.common.data.storage.PreferencesImpl
import com.itis.common.utils.CredentialsValidator
import com.itis.common.utils.ExceptionHandlerDelegate
import com.itis.feature.auth.impl.domain.usecases.SignInUseCase
import com.itis.feature.auth.impl.presentation.screens.signin.SignInViewModel
import com.itis.feature.auth.api.utils.UsersAuthRouter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class SignInModule {

    @Provides
    fun provideMainViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory
    ): SignInViewModel {
        return ViewModelProvider(fragment, factory)[SignInViewModel::class.java]
    }

    @Provides
    @[IntoMap ViewModelKey(SignInViewModel::class)]
    fun provideRegisterViewModel(
        useCase: SignInUseCase,
        router: UsersAuthRouter,
        preferencesImpl: PreferencesImpl,
        exceptionHandlerDelegate: ExceptionHandlerDelegate,
        credentialsValidator: CredentialsValidator
    ): ViewModel {
        return SignInViewModel(useCase,router,preferencesImpl, exceptionHandlerDelegate, credentialsValidator)
    }
}