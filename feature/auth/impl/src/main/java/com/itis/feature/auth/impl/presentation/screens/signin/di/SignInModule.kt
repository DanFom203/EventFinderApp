package com.itis.feature.auth.impl.presentation.screens.signin.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.common.di.viewmodel.ViewModelKey
import com.itis.common.di.viewmodel.ViewModelModule
import com.itis.common.storage.PreferencesImpl
import com.itis.feature.auth.impl.domain.usecases.SignInUseCase
import com.itis.feature.auth.impl.presentation.screens.signin.SignInViewModel
import com.itis.feature.auth.impl.utils.UsersAuthRouter
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
        preferencesImpl: PreferencesImpl
    ): ViewModel {
        return SignInViewModel(useCase,router,preferencesImpl)
    }
}