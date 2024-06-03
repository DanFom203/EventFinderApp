package com.example.feature.profile.impl.presentation.screens.change_credentials.di

import androidx.fragment.app.Fragment
import com.example.feature.profile.impl.presentation.screens.change_credentials.ChangeCredentialsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [ChangeCredentialsModule::class]
)
interface ChangeCredentialsComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment
        ): ChangeCredentialsComponent
    }
    fun inject(fragment: ChangeCredentialsFragment)
}