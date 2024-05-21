package com.itis.feature.auth.impl.di

import android.content.Context
import com.itis.feature.auth.impl.presentation.screens.signup.di.SignUpComponent
import com.itis.common.di.CommonApi
import com.itis.common.di.scope.FeatureScope
import com.itis.feature.auth.api.di.AuthFeatureApi
import com.itis.feature.auth.impl.presentation.screens.initial.di.InitialComponent
import com.itis.feature.auth.impl.presentation.screens.signin.di.SignInComponent
import com.itis.feature.auth.impl.presentation.screens.splash.di.SplashComponent
import com.itis.feature.auth.impl.utils.UsersAuthRouter
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    modules = [
        AuthFeatureModule::class
    ],
    dependencies = [AuthFeatureDependencies::class]
)
interface AuthFeatureComponent : AuthFeatureApi {

    fun signUpComponentFactory(): SignUpComponent.Factory

    fun signInComponentFactory(): SignInComponent.Factory

    fun initialComponentFactory(): InitialComponent.Factory

    fun splashScreenFactory(): SplashComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        @BindsInstance
        fun router(usersAuthRouter: UsersAuthRouter): Builder
        fun withDependencies(deps: AuthFeatureDependencies): Builder
        fun build(): AuthFeatureComponent
    }

    @Component(
        dependencies = [
            CommonApi::class
        ]
    )
    interface AuthFeatureDependenciesComponent : AuthFeatureDependencies

}