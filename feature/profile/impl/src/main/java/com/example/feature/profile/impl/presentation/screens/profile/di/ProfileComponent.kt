package com.example.feature.profile.impl.presentation.screens.profile.di

import androidx.fragment.app.Fragment
import com.example.feature.profile.impl.presentation.screens.profile.ProfileFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [ProfileModule::class]
)
interface ProfileComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment
        ): ProfileComponent
    }
    fun inject(fragment: ProfileFragment)
}