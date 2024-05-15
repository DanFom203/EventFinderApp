package com.itis.eventfinderapp

import android.app.Application
import com.itis.common.di.CommonApi
import com.itis.common.di.FeatureContainer
import com.itis.eventfinderapp.di.app.AppComponent
import com.itis.eventfinderapp.di.deps.ComponentDependenciesProvider
import com.itis.eventfinderapp.di.deps.FeatureHolderManager
import javax.inject.Inject

open class App : Application(), FeatureContainer {

    @Inject lateinit var featureHolderManager: FeatureHolderManager

    @Inject lateinit var dependencies: ComponentDependenciesProvider

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = AppComponent.init(this)
        appComponent.inject(this)
    }

    override fun <T> getFeature(key: Class<*>): T {
        return featureHolderManager.getFeature<T>(key)!!
    }

    override fun releaseFeature(key: Class<*>) {
        featureHolderManager.releaseFeature(key)
    }

    override fun commonApi(): CommonApi {
        return appComponent
    }
}