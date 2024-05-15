package com.itis.feature.auth.impl.presentation.screens.splash.di

import androidx.fragment.app.Fragment
import com.itis.feature.auth.impl.presentation.screens.splash.SplashScreenFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [SplashModule::class]
)
interface SplashComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment
        ): SplashComponent
    }
    fun inject(fragment: SplashScreenFragment)
}