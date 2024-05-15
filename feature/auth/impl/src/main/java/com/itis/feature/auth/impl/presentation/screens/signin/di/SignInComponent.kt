package com.itis.feature.auth.impl.presentation.screens.signin.di

import androidx.fragment.app.Fragment
import com.itis.feature.auth.impl.presentation.screens.signin.SignInFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [SignInModule::class]
)
interface SignInComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment
        ): SignInComponent
    }
    fun inject(fragment: SignInFragment)
}