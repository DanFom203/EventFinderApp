package com.itis.feature.auth.impl.presentation.screens.initial.di

import androidx.fragment.app.Fragment
import com.itis.feature.auth.impl.presentation.screens.initial.InitialFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [InitialModule::class]
)
interface InitialComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment
        ): InitialComponent
    }
    fun inject(fragment: InitialFragment)
}