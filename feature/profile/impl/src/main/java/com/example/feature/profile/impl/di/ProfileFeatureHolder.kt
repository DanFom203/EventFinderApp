package com.example.feature.profile.impl.di

import android.content.Context
import com.example.feature.profile.api.utils.ProfileFeatureRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itis.common.di.FeatureApiHolder
import com.itis.common.di.FeatureContainer
import com.itis.common.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class ProfileFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val profileFeatureRouter: ProfileFeatureRouter,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val context: Context
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
        val profileFeatureDependencies = DaggerProfileFeatureComponent_ProfileFeatureDependenciesComponent.builder()
            .commonApi(commonApi())
            .build()
        return DaggerProfileFeatureComponent.builder()
            .context(context)
            .db(db)
            .auth(auth)
            .storage(storage)
            .router(profileFeatureRouter)
            .withDependencies(profileFeatureDependencies)
            .build()
    }
}