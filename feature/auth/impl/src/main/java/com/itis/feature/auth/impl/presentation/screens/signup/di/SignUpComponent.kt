package com.itis.feature.auth.impl.presentation.screens.signup.di

import androidx.fragment.app.Fragment
import com.itis.feature.auth.impl.presentation.screens.signup.SignUpFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [SignUpModule::class]
)
interface SignUpComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment:Fragment
        ): SignUpComponent
    }
    fun inject(fragment: SignUpFragment)
}