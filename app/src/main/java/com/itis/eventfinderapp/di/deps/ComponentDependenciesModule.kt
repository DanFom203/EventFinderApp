package com.itis.eventfinderapp.di.deps

import com.itis.eventfinderapp.di.app.AppComponent
import com.itis.eventfinderapp.di.main.di.MainDependencies
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ComponentDependenciesModule {

    @Binds
    @[IntoMap ComponentDependenciesKey(MainDependencies::class)]
    fun provideMainDependencies(component: AppComponent): ComponentDependencies
}