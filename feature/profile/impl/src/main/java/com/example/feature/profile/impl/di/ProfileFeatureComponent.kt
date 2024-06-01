package com.example.feature.profile.impl.di

import android.content.Context
import com.example.feature.profile.api.di.ProfileFeatureApi
import com.example.feature.profile.impl.presentation.screens.profile.di.ProfileComponent
import com.example.feature.profile.impl.utils.ProfileFeatureRouter
import com.google.firebase.firestore.FirebaseFirestore
import com.itis.common.di.CommonApi
import com.itis.common.di.scope.FeatureScope
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    modules = [
        ProfileFeatureModule::class
    ],
    dependencies = [ProfileFeatureDependencies::class]
)
interface ProfileFeatureComponent : ProfileFeatureApi {

    fun profileComponentFactory(): ProfileComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        @BindsInstance
        fun db(firestore: FirebaseFirestore): Builder
        @BindsInstance
        fun router(profileRouter: ProfileFeatureRouter): Builder
        fun withDependencies(deps: ProfileFeatureDependencies): Builder
        fun build(): ProfileFeatureComponent
    }

    @Component(
        dependencies = [
            CommonApi::class
        ]
    )
    interface ProfileFeatureDependenciesComponent : ProfileFeatureDependencies
}