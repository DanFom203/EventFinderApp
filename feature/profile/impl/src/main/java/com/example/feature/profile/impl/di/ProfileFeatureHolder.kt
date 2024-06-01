package com.example.feature.profile.impl.di

import android.content.Context
import com.example.feature.profile.impl.utils.ProfileFeatureRouter
import com.google.firebase.firestore.FirebaseFirestore
import com.itis.common.di.FeatureApiHolder
import com.itis.common.di.FeatureContainer
import com.itis.common.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class ProfileFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val profileFeatureRouter: ProfileFeatureRouter,
    private val db: FirebaseFirestore,
    private val context: Context
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
        val profileFeatureDependencies = DaggerProfileFeatureComponent_ProfileFeatureDependenciesComponent.builder()
            .commonApi(commonApi())
            .build()
        return DaggerProfileFeatureComponent.builder()
            .context(context)
            .db(db)
            .router(profileFeatureRouter)
            .withDependencies(profileFeatureDependencies)
            .build()
    }
}