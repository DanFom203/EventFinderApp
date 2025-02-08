package com.itis.feature.auth.impl.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.itis.common.di.FeatureApiHolder
import com.itis.common.di.FeatureContainer
import com.itis.common.di.scope.ApplicationScope
import com.itis.feature.auth.api.utils.UsersAuthRouter
import javax.inject.Inject

@ApplicationScope
class AuthFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val usersAuthRouter: UsersAuthRouter,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val context:Context
) : FeatureApiHolder(featureContainer) {
    override fun initializeDependencies(): Any {
        val authFeatureDependencies = DaggerAuthFeatureComponent_AuthFeatureDependenciesComponent.builder()
            .commonApi(commonApi())
            .build()
        return DaggerAuthFeatureComponent.builder()
            .context(context)
            .auth(auth)
            .db(db)
            .withDependencies(authFeatureDependencies)
            .router(usersAuthRouter)
            .build()
    }
}
