package com.itis.eventfinderapp.di.app

import com.itis.common.di.modules.FirebaseModule
import com.itis.common.di.modules.RoomDatabaseModule
import com.itis.common.di.CommonApi
import com.itis.common.di.modules.CommonModule
import com.itis.common.di.modules.NetworkModule
import com.itis.common.di.modules.PreferencesModule
import com.itis.common.di.scope.ApplicationScope
import com.itis.eventfinderapp.App
import com.itis.eventfinderapp.di.deps.ComponentDependenciesModule
import com.itis.eventfinderapp.di.deps.ComponentHolderModule
import com.itis.eventfinderapp.di.main.di.MainDependencies
import com.itis.feature.kudago.api.di.ApiNetworkModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        CommonModule::class,
        NetworkModule::class,
        ApiNetworkModule::class,
        PreferencesModule::class,
        NavigationModule::class,
        ComponentHolderModule::class,
        ComponentDependenciesModule::class,
        FeatureManagerModule::class,
        FirebaseModule::class,
        RoomDatabaseModule::class
    ],
    dependencies = []
)
interface AppComponent : MainDependencies, CommonApi {

    companion object {

        fun init(application: App): AppComponent {
            return DaggerAppComponent
                .builder()
                .application(application)
                .build()
        }
    }

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}