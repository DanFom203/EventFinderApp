package com.itis.feature.auth.impl.di

import android.content.Context
import com.itis.common.di.FeatureApiHolder
import com.itis.common.di.FeatureContainer
import com.itis.common.di.scope.ApplicationScope
import com.itis.feature.auth.impl.utils.UsersAuthRouter
import javax.inject.Inject

@ApplicationScope
class AuthFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val usersAuthRouter: UsersAuthRouter,
    private val context:Context
) : FeatureApiHolder(featureContainer) {
    override fun initializeDependencies(): Any {
        val authFeatureDependencies = DaggerAuthFeatureComponent_AuthFeatureDependenciesComponent.builder()
            .commonApi(commonApi())
            .build()
        return DaggerAuthFeatureComponent.builder()
            .context(context)
            .withDependencies(authFeatureDependencies)
            .router(usersAuthRouter)
            .build()

    }


}
