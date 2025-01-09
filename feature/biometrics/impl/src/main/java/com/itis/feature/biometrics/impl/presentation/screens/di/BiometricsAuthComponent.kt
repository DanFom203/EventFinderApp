package com.itis.feature.biometrics.impl.presentation.screens.di

import androidx.fragment.app.Fragment
import com.itis.feature.biometrics.impl.presentation.screens.BiometricsAuthFragment
import dagger.BindsInstance
import dagger.Subcomponent

interface BiometricsAuthComponent {
    @Subcomponent(
        modules = [BiometricsAuthModule::class]
    )
    interface BiometricsComponent {
        @Subcomponent.Factory
        interface Factory {
            fun create(
                @BindsInstance fragment: Fragment
            ): BiometricsComponent
        }
        fun inject(fragment: BiometricsAuthFragment)
    }
}